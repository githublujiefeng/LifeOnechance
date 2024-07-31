package com.learn.hsm.hsmj;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

public class HsmSession {
	private static final SessionMonitor sSessionMonitor  = new SessionMonitor();

	private static ShareHandle[] sHandles;

	private static int sHsmNumber;
	private static int sBalance;
	private static String[] sIPs;
	private static int sPort;
	private static int sTimeOut;
	
	private static int sPreIndex = -1;
	private int iLastErr = -1;

	// 创建连接
	public HsmSession(String aProfileFile) {
		iLastErr = 0;

		//初始化加密机连接
		try {
			InitSession(aProfileFile);
		}
		catch (Exception e) {
			iLastErr = HsmConst.ERR_CONFIG_FILE;
		}
	}

	private static synchronized void InitSession(String aProfileFile) throws Exception {
		int i, j;
		int nError = 0;

		//1、判断是否已经初始化
		if (sHandles != null)
			return;

		//2、获取配制文件
		try {
			FileInputStream raf = new FileInputStream(aProfileFile);
			Properties pr = new Properties();
			pr.load(raf);

			sHsmNumber = Integer.parseInt( pr.getProperty("NUMBER") );
			sBalance   = Integer.parseInt( pr.getProperty("BALANCE") );
			sPort      = Integer.parseInt( pr.getProperty("PORT") );
			sTimeOut   = Integer.parseInt( pr.getProperty("TIMEOUT") );

			sIPs = new String[sHsmNumber];
			for (i = 0; i < sHsmNumber; i++) {
				sIPs[i] = pr.getProperty( Integer.toString(i+1) );
			}
		}
		catch (Exception e) {
			throw new Exception("读取加密机连接配置文件[" + aProfileFile + "]发生错误。");
		}
		catch (Error err) {
			throw new Exception("读取加密机连接配置文件[" + aProfileFile + "]发生错误。");
		}

		//3、初始化连接池，每加密机依次建立一个连接，以实现负载均衡
		ShareHandle[] tHandle = new ShareHandle[sHsmNumber * sBalance];
		for (i = 0; i < sBalance; i++) {
			for (j = 0; j < sHsmNumber; j++) {
				tHandle[i * sHsmNumber + j] = new ShareHandle(sIPs[j], sPort, sTimeOut);
				if (tHandle[i * sHsmNumber + j].isFault())
					nError++;
			}
		}

		if (nError == sHsmNumber * sBalance) {
			throw new Exception("无法与加密机建立连接。");
		}

		//4、启动连接监控线程
		sHandles = tHandle;
		sSessionMonitor.addHandle(sHandles);
		sSessionMonitor.start();
	}


	public int GetSessionID(){
		int iHandleID = -1;

		//获取可用连接
		for (int loop = 0; loop < (sTimeOut / 20); loop++) {
			iHandleID = getSession();
			if (iHandleID != -1)
				break;
			try {
				Thread.sleep(20);
			}
			catch (InterruptedException e) {
				break;
			}
		}

		return iHandleID;
	}


	private static synchronized int getSession() {
		int i;
		int iHandleID = -1;
		ShareHandle tHandle;
		int tNumOfSession = sHsmNumber * sBalance;

		for (i = sPreIndex + 1; i < tNumOfSession; i++) {
			tHandle = sHandles[i];
			if (tHandle.isUsable()) {
				tHandle.setUsed();
				sPreIndex = i;
				iHandleID = i;
				break;
			}
		}

		if (i == tNumOfSession) {
			for(i = 0; i <= sPreIndex; i++) {
				tHandle = sHandles[i];
				if (tHandle.isUsable()) {
					tHandle.setUsed();
					sPreIndex = i;
					iHandleID = i;
					break;
				}
			}
		}

		return iHandleID;
	}


	public int GetPortConf() {
		return sPort;
	}


	public int GetLastError() {
		return iLastErr;
	}


	public void SetErrCode(int nErrCode) {
		iLastErr = nErrCode;
		return;
	}


	public String ParseErrCode(int nErrCode) {
		String sMeaning;
		switch (nErrCode) {
		case 0:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":操作正确,状态正常";
			break;
		case HsmConst.ERR_CONFIG_FILE:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":配置文件异常";
			break;
		case HsmConst.ERR_CONNECT_HSM:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接密码机失败";
			break;
		case HsmConst.ERR_SENDTO_HSM:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":发送数据至密码机失败";
			break;
		case HsmConst.ERR_RECVFORM_HSM:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":接收密码机数据失败";
			break;
		case HsmConst.ERR_SESSION_END:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接已关闭";
			break;
		case HsmConst.ERR_HANDLE_FAULT:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接句柄状态异常";
			break;
		default:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":异常操作,检查密码机日志";
			break;
		}
		return sMeaning;
	}


	// 发送数据到加密机,正确返回0,失败返回错误码
	public int SendData(int iHandleID,byte[] byteOut, int nLength) {
		ShareHandle tHandle = sHandles[iHandleID];

		if (tHandle.isFault()) {
			System.out.println("HsmSession: hand (err)" );
			return HsmConst.ERR_HANDLE_FAULT;
		}

		try {
			tHandle.write(byteOut, nLength);
		}
		catch (Exception e) {
			tHandle.setFault();
			System.out.println("HsmSession::SendData() - " + e.getMessage());
			return HsmConst.ERR_SENDTO_HSM;
		}
		catch (Error err) {
			tHandle.setFault();
			System.out.println("HsmSession::SendData() - " + err.getMessage());
			return HsmConst.ERR_SENDTO_HSM;
		}

		return HsmConst.T_SUCCESS;
	}


	// 从加密机接收数据,正确返回收到的字节数,否则返回-1
	public int RecvData(int iHandleID,byte[] byteIn) {
		ShareHandle tHandle = sHandles[iHandleID];
		int rcvLen;
		if (tHandle.isFault()) {
			return -1;
		}

		try {
			rcvLen = tHandle.read(byteIn, HsmConst.SECBUF_MAX_SIZE);
		}
		catch (Exception e) {
			tHandle.setFault();
			e.printStackTrace();
			System.out.println("HsmSession::RecvData() - " + e.getMessage());
			return -1;
		}
		catch (Error err) {
			tHandle.setFault();
			err.printStackTrace();
			System.out.println("HsmSession::RecvData() - " + err.getMessage());
			return -1;
		}

		if(rcvLen > 0){
			return rcvLen;
		}else{ 
			tHandle.setFault();
			return -1; 
		}
	}


	public void ReleaseSession(int iHandleID) {
		if (iHandleID >= 0 ) {
			ShareHandle tHandle = sHandles[iHandleID];
			if (tHandle.isUsed()) {
				tHandle.setNotused();
			}
		}
		return ;
	}


	public static synchronized void ClearAllSessions(){
		int i;
		int iTotalSessions = sHsmNumber * sBalance;

		if(sHandles == null)
			return ;

		sSessionMonitor.stopMonitor();

		for(i=0; i<iTotalSessions; i++){
			sHandles[i].releaseSocketHandle();
		}

		sHandles = null;
		return ;
	}
}

class ShareHandle {
	final int FLAG_NOTUSE = 0;
	final int FLAG_USED   = 1;
	final int FLAG_FAULT  = 2;

	private Socket iSocketHandle = null;

	private int iStatus;

	private InputStream iInputStream = null;
	private OutputStream iOutputStream = null;

	private String iIP = null;
	private int iPort = -1;
	private int iTimeout = -1;

	/**
	 * @param aString
	 * @param aPort
	 * @param aTimeOut
	 */
	public ShareHandle(String aIP, int aPort, int aTimeout) {
		iIP = aIP;
		iPort = aPort;
		iTimeout = aTimeout;
		connect();
	}

	public void connect() {
		try {
			iSocketHandle = new Socket();
			InetSocketAddress hsmAddr = new InetSocketAddress(iIP, iPort);
			System.out.println("IP: "+iIP+" Port: "+iPort);
			iSocketHandle.connect(hsmAddr, iTimeout);
			iSocketHandle.setSoTimeout(iTimeout);
			iSocketHandle.setKeepAlive(true);
			iSocketHandle.setReceiveBufferSize(2048);
			iSocketHandle.setTcpNoDelay(true);
			iInputStream = iSocketHandle.getInputStream();
			iOutputStream = iSocketHandle.getOutputStream();
			setNotused();
		}
		catch (IOException e) {
			releaseSocketHandle();
		}
	}

	public void releaseSocketHandle() {
		setFault();

		if (iInputStream != null) {
			try {
				iInputStream.close();
			}
			catch (Exception e) {}
			iInputStream = null;
		}

		if (iOutputStream != null) {
			try {
				iOutputStream.close();
			}
			catch (Exception e) {}
			iOutputStream = null;
		}

		if (iSocketHandle != null) {
			try {
				iSocketHandle.close();
			}
			catch (Exception e) {}
			iSocketHandle = null;
		}
	}

	public void setUsed() {
		iStatus = FLAG_USED;
	}

	public void setNotused() {
		iStatus = FLAG_NOTUSE;
	}

	public void setFault() {
		iStatus = FLAG_FAULT;
	}

	public int getStatus() {
		return iStatus;
	}

	public boolean isUsed() {
		return (iStatus == FLAG_USED);
	}

	public boolean isUsable() {
		return (iStatus == FLAG_NOTUSE);
	}

	public boolean isFault() {
		return (iStatus == FLAG_FAULT);
	}

	/**
	 * @param aByteOut
	 * @param aI
	 * @param aLength
	 * @throws IOException 
	 */
	public void write(byte[] aByteOut, int aLength) throws IOException {
		iOutputStream.write(aByteOut, 0, aLength);
		iOutputStream.flush();
	}

	/**
	 * @param aByteIn
	 * @param aI
	 * @throws IOException 
	 */
	public int read(byte[] aByteIn, int aBufferSize) throws IOException {
		return iInputStream.read(aByteIn, 0, aBufferSize);
	}
}

class SessionMonitor extends Thread {
	private static ShareHandle[] sHandle;
	private boolean bStop;

	public void addHandle(ShareHandle[] aHandle) {
		sHandle = aHandle;
	}

	public void stopMonitor()
	{
		bStop = true;
	} 

	private void testConnect(ShareHandle tHandle)
	{
		byte[] aBuffer=new byte[64];
		//aBuffer[1] = (byte)0x01;
		aBuffer[0] = (byte)0x00;
		aBuffer[1] = (byte)0x1a;
		System.arraycopy("01FFFFFFFF1234567800000000".getBytes(), 0, aBuffer, 2, 26);

		if(tHandle.isUsable()){
			tHandle.setUsed();
			try{
				//tHandle.write(aBuffer,3);
				tHandle.write(aBuffer,28);
				tHandle.read(aBuffer,64);
			}
			catch (Exception e) {
				tHandle.setFault();
				return;
			}
			catch (Error err) {
				tHandle.setFault();
				return;
			}
			tHandle.setNotused();
		}
	}

	public void run() {
		int iTest = 0;
		int i;

		while (!bStop) {
			//			System.out.println("SessionMonitor::Check connection pool ! ");
			try {
				for (i = 0; i < sHandle.length; i++){
					//					System.out.println("SessionMonitor::Status of sHandle[" + i + "] = " + sHandle[i].getStatus());
					if (sHandle[i].isFault()) {
						//						System.out.println( "SessionMonitor::reconnect sHandle[" + i +"]");
						sHandle[i].releaseSocketHandle();
						sHandle[i].connect();
					}
				}
			}
			catch (Exception e) {
				System.out.println("SessionMonitor::run() - " + e.getMessage());
			}
			catch (Error err) {
				System.out.println("SessionMonitor::run() - " + err.getMessage());
			}

			try{
				sleep(HsmConst.MONI_INTERVAL);
			}
			catch (Exception e1){}

			iTest++;
			if( iTest >= HsmConst.TEST_INTERVAL){
				iTest = 0;
				for (i = 0; i < sHandle.length; i++){
					System.out.println("SessionMonitor::test connect[" + i + "]");
					testConnect(sHandle[i]);
				}
			}
		}
	}

}
