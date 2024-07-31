package com.learn.hsm.hsmj;

public class HsmApp {
	private int iLastErr;
	public static boolean printlog = true;

	// 构造函数：
	public HsmApp() {
		iLastErr = 0;
	}

	public int GetLastError(){
		return iLastErr;
	}

	// 常用函数：数据打印：HEX格式
	static public void OutputDataHex(String sInfo, byte[] byteIn, int nDataLen) {
		int i, j, n, prev;

		if ( ! printlog )
			return;

		System.out.println("[" + sInfo + "]" + "length" + "[" + nDataLen + "]");
		prev = n = 0;
		for (i = 0; i < nDataLen; i++) {
			if (i == (prev + 16)) {
				System.out.print("    ;");
				for (j = prev; j < prev + 16; j++) {
					if (Character.isLetter((char) (byteIn[j] & 0xff)) == true) {
						System.out.print((char) byteIn[j]);
					} else if (Character.isDigit((char) (byteIn[j] & 0xff)) == true) {
						System.out.print((char) byteIn[j]);
					}
					else {
						System.out.print(" ");
					}
				}
				prev += 16;
				System.out.println();
			}
			if (Integer.toHexString(byteIn[i] & 0xff).length() == 1) {
				System.out.print("0" + Integer.toHexString(byteIn[i] & 0xff) + " ");
			}
			else {
				System.out.print(Integer.toHexString(byteIn[i] & 0xff) + " ");
			}
		}
		if (i != prev) {
			n = i;
			for (; i < prev + 16; i++) {
				System.out.print("   ");
			}
		}
		System.out.print("    ;");
		for (i = prev; i < n; i++) {
			if (Character.isLetter((char) byteIn[i]) == true) {
				System.out.print((char) byteIn[i]);
			} else if (Character.isDigit((char) (byteIn[i] & 0xff)) == true) {
				System.out.print((char) byteIn[i]);
			} else {
				System.out.print(" ");
			}
		}
		System.out.println();
	}

	static public boolean Str2Hex(byte[] in, byte[] out, int len) {
		byte[] asciiCode = { 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

		if (len > in.length) {
			return false;
		}

		if (len % 2 != 0) {
			return false;
		}

		byte[] temp = new byte[len];

		for (int i = 0; i < len; i++) {
			if (in[i] >= 0x30 && in[i] <= 0x39) {
				temp[i] = (byte) (in[i] - 0x30);
			}
			else if (in[i] >= 0x41 && in[i] <= 0x46) {
				temp[i] = asciiCode[in[i] - 0x41];
			}
			else if (in[i] >= 0x61 && in[i] <= 0x66) {
				temp[i] = asciiCode[in[i] - 0x61];
			}
			else {
				return false;
			}
		}

		for (int i = 0; i < len / 2; i++) {
			out[i] = (byte) (temp[2 * i] * 16 + temp[2 * i + 1]);
		}

		return true;
	}

	static public boolean Hex2Str(byte[] in, byte[] out, int len) {
		byte[] asciiCode = { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46 };

		if (len > in.length) {
			return false;
		}

		byte[] temp = new byte[2 * len];

		for (int i = 0; i < len; i++) {
			temp[2 * i] = (byte) ((in[i] & 0xf0) / 16);
			temp[2 * i + 1] = (byte) (in[i] & 0x0f);
		}

		for (int i = 0; i < 2 * len; i++) {
			if (temp[i] <= 9 && temp[i] >= 0) {
				out[i] = (byte) (temp[i] + 0x30);
			}
			else {
				out[i] = asciiCode[temp[i] - 0x0a];
			}
		}

		return true;
	}

	public static String byte2hex(byte[] b) { // 二行制转字符
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			}
			else {
				hs = hs + stmp;

			}
			if (n < b.length - 1) {
				hs = hs + " ";
			}
		}

		return hs.toUpperCase();
	}


	private int HSM_LINK(HsmSession hSession, byte[] bSecBufferIn, int iSndLen, byte[] bSecBufferOut)
	{
		int iSessionID, nResult = -1;

		//Get a session
		iSessionID = hSession.GetSessionID();
		if(iSessionID == -1){
			System.out.println("Get session error, iSessionID=" + iSessionID);	
			return HsmConst.ERR_HANDLE_FAULT;
		}

		// send message
		HsmApp.OutputDataHex( "HSM_LINK SEND", bSecBufferIn, iSndLen );
		nResult = hSession.SendData( iSessionID, bSecBufferIn, iSndLen );
		if ( nResult != HsmConst.T_SUCCESS ) {
			System.out.println("hSession.SendData( iSessionID=" + iSessionID + " ) return : " + nResult);
			return nResult;
		}

		//receive message
		nResult = hSession.RecvData(iSessionID,bSecBufferOut);

		//		HsmApp.OutputDataHex("SEND",bSecBufferIn, bSecBufferIn[1] & 0xff);
		//		HsmApp.OutputDataHex("RECV",bSecBufferOut,bSecBufferOut[1] & 0xff);

		if(nResult < 0) {
			System.out.println("hSession.RecvData( iSessionID=" + iSessionID + " ) return : " + nResult);
			return HsmConst.ERR_RECVFORM_HSM;
		}

		hSession.ReleaseSession(iSessionID);
		if (bSecBufferOut[2] != 'A') {
			return (bSecBufferOut[3] & 0xFF) << 8 | (bSecBufferOut[4] & 0xFF);
		}
		//System.out.println("Realse session ID = " + iSessionID);
		return HsmConst.T_SUCCESS;
	}

	//转加密PIN
	public int ConvertPIN(HsmSession hSession, byte[] pinIn, byte[] pinOut)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;

		bSecBufferIn[0] = (byte)0x00;
		bSecBufferIn[1] = (byte)26;
		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'1';

		System.arraycopy(pinIn, 0, bSecBufferIn, 4, 24);
		iSndLen = 2 + 2 + 24;

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, pinOut, 0, 24);

		return HsmConst.T_SUCCESS;
	}

	//加密明文PIN
	public int EncryptPIN(HsmSession hSession, byte[] strPin, byte[] pinOut)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int i, pinlen;

		for(i = 0; i <= 12; i++)
			if(strPin[i] < '0' || strPin[i] > '9')
				break;
		if(i < 4 || i == 12)
			return HsmConst.EPIN_LENGTH;
		pinlen = i;

		bSecBufferIn[0] = (byte)0x00;
		bSecBufferIn[1] = (byte)(pinlen + 4);
		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'2';
		bSecBufferIn[4] = (byte)0;
		bSecBufferIn[5] = (byte)pinlen;

		System.arraycopy(strPin, 0, bSecBufferIn, 6, pinlen);
		iSndLen = 2 + 2 + 2 + pinlen;

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, pinOut, 0, 24);

		return HsmConst.T_SUCCESS;
	}

	//计算MAC
	public int GenerateMAC(HsmSession hSession, int dataLen, byte[] data, byte[] mac)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;

		if(dataLen > HsmConst.SECBUF_MAX_SIZE - 4)
			return HsmConst.EMES_TOO_LONG;

		bSecBufferIn[0] = (byte)((dataLen + 4) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((dataLen + 4) & 0xFF);

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'3';

		bSecBufferIn[4] = (byte)(dataLen >> 8 & 0xFF);
		bSecBufferIn[5] = (byte)(dataLen & 0xFF);

		System.arraycopy(data, 0, bSecBufferIn, 6, dataLen);
		iSndLen = 6 + dataLen;

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, mac, 0, 16);

		return HsmConst.T_SUCCESS;
	}

	//加密网银密码
	public int EncryptPassword(HsmSession hSession, String strAcco, String strPassword, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen = strAcco.length();
		int pwdLen = strPassword.length();

		//		bSecBufferIn[0] = (byte)((accLen + pwdLen + 3) >> 8 & 0xFF);
		//		bSecBufferIn[1] = (byte)((accLen + pwdLen + 3) & 0xFF);
		bSecBufferIn[0] = (byte)((accLen + pwdLen + 4) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((accLen + pwdLen + 4) & 0xFF);

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'4';

		bSecBufferIn[4] = (byte)(accLen & 0xFF);
		System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, 5, accLen);
		bSecBufferIn[5+accLen] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen, pwdLen);

		iSndLen = accLen + pwdLen + 6;

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, CipherPwd, 0, bSecBufferOut[1] - 1);

		return HsmConst.T_SUCCESS;
	}

	//校验网银密码
	public int VerifyPassword(HsmSession hSession, String strAcco, String strPassword, String strCipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen = strAcco.length();
		int pwdLen = strPassword.length();
		int ciphLen = strCipherPwd.length();

		//bSecBufferIn[0] = (byte)((accLen + pwdLen + ciphLen + 4) >> 8 & 0xFF);
		//bSecBufferIn[1] = (byte)((accLen + pwdLen + ciphLen + 4) & 0xFF);
		bSecBufferIn[0] = (byte)((accLen + pwdLen + ciphLen + 5) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((accLen + pwdLen + ciphLen + 5) & 0xFF);

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'5';

		bSecBufferIn[4] = (byte)(accLen & 0xFF);
		System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, 5, accLen);
		bSecBufferIn[5+accLen] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen, pwdLen);
		bSecBufferIn[6+accLen+pwdLen] = (byte)(ciphLen & 0xFF);
		System.arraycopy(strCipherPwd.getBytes(), 0, bSecBufferIn, 7 + accLen + pwdLen, ciphLen);

		iSndLen = accLen + pwdLen + ciphLen + 7;

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		return nResult;
	}

	//网银密码转换成互联网金融密码
	public int TransPwdWY2NF(HsmSession hSession, String strAccoIn, String strPassword, String strAccoOut, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen1 = strAccoIn.length();
		int accLen2 = strAccoOut.length();
		int pwdLen = strPassword.length();


		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'6';

		bSecBufferIn[4] = (byte)(accLen1 & 0xFF);
		System.arraycopy(strAccoIn.getBytes(), 0, bSecBufferIn, 5, accLen1);
		bSecBufferIn[5+accLen1] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen1, pwdLen);

		iSndLen = accLen1 + pwdLen + 6;
		bSecBufferIn[iSndLen] = (byte)(accLen2 & 0xFF);
		System.arraycopy(strAccoOut.getBytes(), 0, bSecBufferIn, 1 + iSndLen, accLen2);
		iSndLen += (1 + accLen2);

		bSecBufferIn[0] = (byte)((iSndLen -2) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((iSndLen -2) & 0xFF);

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, CipherPwd, 0, bSecBufferOut[1] - 1);

		return HsmConst.T_SUCCESS;
	}


	public int TransPwdHX2NF(HsmSession hSession, byte[] bPinBlockIn, String strAccoOut, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen = strAccoOut.length();

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'7';

		System.arraycopy(bPinBlockIn, 0, bSecBufferIn, 4, 24);
		iSndLen = 4 + 24;
		bSecBufferIn[iSndLen] = (byte)(accLen & 0xFF);
		System.arraycopy(strAccoOut.getBytes(), 0, bSecBufferIn, 1 + iSndLen, accLen);
		iSndLen += (1 + accLen);

		bSecBufferIn[0] = (byte)((iSndLen -2) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((iSndLen -2) & 0xFF);

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, CipherPwd, 0, bSecBufferOut[1] - 1);

		return HsmConst.T_SUCCESS;
	}

	public int TransPwdNF2HX(HsmSession hSession, String strAccoIn, String strPassword, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen1 = strAccoIn.length();
		//int accLen2 = strAccoOut.length();
		int pwdLen = strPassword.length();


		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'8';

		bSecBufferIn[4] = (byte)(accLen1 & 0xFF);
		System.arraycopy(strAccoIn.getBytes(), 0, bSecBufferIn, 5, accLen1);

		bSecBufferIn[5+accLen1] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen1, pwdLen);

		iSndLen = accLen1 + pwdLen + 6;

		bSecBufferIn[0] = (byte)((iSndLen -2) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((iSndLen -2) & 0xFF);

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, CipherPwd, 0, bSecBufferOut[1] - 1);

		return HsmConst.T_SUCCESS;
	}


	public int TransPwdNF2NF(HsmSession hSession, String strAccoIn, String strPassword, String strAccoOut, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen1 = strAccoIn.length();
		int accLen2 = strAccoOut.length();
		int pwdLen = strPassword.length();


		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'9';

		bSecBufferIn[4] = (byte)(accLen1 & 0xFF);
		System.arraycopy(strAccoIn.getBytes(), 0, bSecBufferIn, 5, accLen1);
		bSecBufferIn[5+accLen1] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen1, pwdLen);

		iSndLen = accLen1 + pwdLen + 6;
		bSecBufferIn[iSndLen] = (byte)(accLen2 & 0xFF);
		System.arraycopy(strAccoOut.getBytes(), 0, bSecBufferIn, 1 + iSndLen, accLen2);
		iSndLen += (1 + accLen2);

		bSecBufferIn[0] = (byte)((iSndLen -2) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((iSndLen -2) & 0xFF);

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, CipherPwd, 0, bSecBufferOut[1] - 1);

		return HsmConst.T_SUCCESS;
	}
	//互联网金融密码转换成网银密码
	public int TransPwdNF2WY(HsmSession hSession, String strAccoIn, String strPassword, String strAccoOut, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen1 = strAccoIn.length();
		int accLen2 = strAccoOut.length();
		int pwdLen = strPassword.length();


		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'A';

		bSecBufferIn[4] = (byte)(accLen1 & 0xFF);
		System.arraycopy(strAccoIn.getBytes(), 0, bSecBufferIn, 5, accLen1);
		bSecBufferIn[5+accLen1] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen1, pwdLen);

		iSndLen = accLen1 + pwdLen + 6;
		bSecBufferIn[iSndLen] = (byte)(accLen2 & 0xFF);
		System.arraycopy(strAccoOut.getBytes(), 0, bSecBufferIn, 1 + iSndLen, accLen2);
		iSndLen += (1 + accLen2);

		bSecBufferIn[0] = (byte)((iSndLen -2) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((iSndLen -2) & 0xFF);

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		if (nResult != HsmConst.T_SUCCESS) {
			return nResult;
		}

		System.arraycopy(bSecBufferOut, 3, CipherPwd, 0, bSecBufferOut[1] - 1);

		return HsmConst.T_SUCCESS;
	}

	//校验网银密码
	public int VerifyWYPassword(HsmSession hSession, String strAcco, String strPassword, String strCipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];

		int nResult, iSndLen;
		int accLen = strAcco.length();
		int pwdLen = strPassword.length();
		int ciphLen = strCipherPwd.length();

		//bSecBufferIn[0] = (byte)((accLen + pwdLen + ciphLen + 4) >> 8 & 0xFF);
		//bSecBufferIn[1] = (byte)((accLen + pwdLen + ciphLen + 4) & 0xFF);
		bSecBufferIn[0] = (byte)((accLen + pwdLen + ciphLen + 5) >> 8 & 0xFF);
		bSecBufferIn[1] = (byte)((accLen + pwdLen + ciphLen + 5) & 0xFF);

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'B';

		bSecBufferIn[4] = (byte)(accLen & 0xFF);
		System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, 5, accLen);
		bSecBufferIn[5+accLen] = (byte)(pwdLen & 0xFF);
		System.arraycopy(strPassword.getBytes(), 0, bSecBufferIn, 6 + accLen, pwdLen);
		bSecBufferIn[6+accLen+pwdLen] = (byte)(ciphLen & 0xFF);
		System.arraycopy(strCipherPwd.getBytes(), 0, bSecBufferIn, 7 + accLen + pwdLen, ciphLen);

		iSndLen = accLen + pwdLen + ciphLen + 7;

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);		
		return nResult;
	}

	//校验互联网金融密码-20200614
	public int VerifyWYPasswordSm2(HsmSession hSession, byte keyFlag, int keyIndex,String keyName,String strAcco, String strInPassword, byte algFlag,String strCipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];
		int wlen,i;

		int nResult, iSndLen;
		int accLen = strAcco.length();
		int pwdLen = strInPassword.length();
		int ciphLen = strCipherPwd.length();

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'C';
		wlen = 2 + 2;

		if((keyFlag != 1)&&(keyFlag != 2)&&(keyFlag != 0)){
			return HsmConst.EPARA_INPUT_DATA;
		}

		bSecBufferIn[wlen] = keyFlag;
		wlen += 1;
		if ( keyFlag != 0 ) {
			bSecBufferIn[wlen] = (byte)(keyIndex >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(keyIndex & 0xFF);
			wlen += 2;
			System.arraycopy(keyName.getBytes(), 0, bSecBufferIn, wlen, 8);
			wlen += 8;


			byte[] tmpPin = new byte[pwdLen/2];
			if(Str2Hex(strInPassword.getBytes(),tmpPin,pwdLen) != true){
				return HsmConst.EPARA_INPUT_DATA;
			}
			pwdLen = pwdLen/2;
			bSecBufferIn[wlen] = (byte)(pwdLen >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(pwdLen & 0xFF);
			wlen += 2;
			System.arraycopy(tmpPin, 0, bSecBufferIn, wlen, pwdLen);
			wlen += pwdLen;

			bSecBufferIn[wlen] = (byte)(0x02);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;

			bSecBufferIn[wlen] = (byte)(accLen & 0xFF);
			wlen += 1;

			System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, wlen, accLen);
			wlen += accLen;

			iSndLen = wlen;
		}else{
			bSecBufferIn[wlen] = (byte)(pwdLen >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(pwdLen & 0xFF);
			wlen += 2;
			System.arraycopy(strInPassword.getBytes(), 0, bSecBufferIn, wlen, pwdLen);
			wlen += pwdLen;

			bSecBufferIn[wlen] = (byte)(0x02);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;

			bSecBufferIn[wlen] = (byte)(0x02);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;

			bSecBufferIn[wlen] = (byte)(accLen & 0xFF);
			wlen += 1;

			System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, wlen, accLen);
			wlen += accLen;

			iSndLen = wlen;
		}

		wlen -= 2;
		bSecBufferIn[0] = (byte)((wlen >> 8) & 0xFF);
		bSecBufferIn[1] = (byte)(wlen & 0xFF);
		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);
		if(nResult != HsmConst.T_SUCCESS){
			return nResult;
		}

		byte[] tmp = new byte[ciphLen/2];
		if(Str2Hex(strCipherPwd.getBytes(), tmp, ciphLen) != true){
			return HsmConst.EPARA_INPUT_DATA;
		}

		if(bSecBufferOut[3] != ciphLen/2){
			return HsmConst.EPIN_VERIFY;
		}

		for(i=0;i<(ciphLen/2);i++){
			if(tmp[i] != bSecBufferOut[4+i]){
				return HsmConst.EPIN_VERIFY;
			}
		}

		return HsmConst.T_SUCCESS;
	}

	//转加密		
	public int TranPasswordSm2(HsmSession hSession, byte keyFlag, int keyIndex,String keyName,String strAcco, String strInPassword, byte algFlag,int[] outlen,byte[] strCipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];
		int wlen;
		int ciphLen;

		int nResult, iSndLen;
		int accLen = strAcco.length();
		int pwdLen = strInPassword.length();

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'C';
		wlen = 2 + 2;

		if((keyFlag != 1)&&(keyFlag != 2)&&(keyFlag != 0)){
			return HsmConst.EPARA_INPUT_DATA;
		}
		bSecBufferIn[wlen] = keyFlag;
		wlen += 1;
		if ( keyFlag != 0 ) {

			bSecBufferIn[wlen] = (byte)(keyIndex >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(keyIndex & 0xFF);
			wlen += 2;

			System.arraycopy(keyName.getBytes(), 0, bSecBufferIn, wlen, 8);
			wlen += 8;

			byte[] tmpPin = new byte[pwdLen/2];
			if(Str2Hex(strInPassword.getBytes(),tmpPin,pwdLen) != true){
				return HsmConst.EPARA_INPUT_DATA;
			}
			pwdLen = pwdLen/2;
			bSecBufferIn[wlen] = (byte)(pwdLen >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(pwdLen & 0xFF);
			wlen += 2;
			System.arraycopy(tmpPin, 0, bSecBufferIn, wlen, pwdLen);
			wlen += pwdLen;

			bSecBufferIn[wlen] = (byte)(0x02);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;

			bSecBufferIn[wlen] = (byte)(accLen & 0xFF);
			wlen += 1;

			System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, wlen, accLen);
			wlen += accLen;

			iSndLen = wlen;
		}else{
			bSecBufferIn[wlen] = (byte)(pwdLen >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(pwdLen & 0xFF);
			wlen += 2;
			System.arraycopy(strInPassword.getBytes(), 0, bSecBufferIn, wlen, pwdLen);
			wlen += pwdLen;

			bSecBufferIn[wlen] = (byte)(0x02);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;

			bSecBufferIn[wlen] = (byte)(accLen & 0xFF);
			wlen += 1;

			System.arraycopy(strAcco.getBytes(), 0, bSecBufferIn, wlen, accLen);
			wlen += accLen;

			iSndLen = wlen;
		}

		wlen -= 2;
		bSecBufferIn[0] = (byte)((wlen >> 8) & 0xFF);
		bSecBufferIn[1] = (byte)(wlen & 0xFF);

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);
		if(nResult != HsmConst.T_SUCCESS){
			return nResult;
		}

		ciphLen = bSecBufferOut[3];
		byte[] tmp = new byte[ciphLen];

		System.arraycopy(bSecBufferOut, 4, tmp, 0, ciphLen);
		if( Hex2Str(tmp, strCipherPwd, ciphLen) != true){
			return HsmConst.EPARA_INPUT_DATA;
		}

		outlen[0] = ciphLen*2;

		return HsmConst.T_SUCCESS;
	}

	//2020 06 14			
	public int TransPwdSm2NX(HsmSession hSession, byte keyFlag, int keyIndex,String keyName,String strInPassword, byte algFlag,int[] outLen, byte[] CipherPwd)
	{
		byte[] bSecBufferIn  = new byte[HsmConst.SECBUF_MAX_SIZE];
		byte[] bSecBufferOut = new byte[HsmConst.SECBUF_MAX_SIZE];
		int wlen;

		int nResult, iSndLen;
		int pwdLen = strInPassword.length();

		bSecBufferIn[2] = (byte)'0';
		bSecBufferIn[3] = (byte)'C';
		wlen = 2 + 2;

		if((keyFlag != 1)&&(keyFlag != 2)&&(keyFlag != 0)){
			return HsmConst.EPARA_INPUT_DATA;
		}
		bSecBufferIn[wlen] = keyFlag;
		wlen += 1;

		if ( keyFlag != 0 ) {
			bSecBufferIn[wlen] = (byte)(keyIndex >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(keyIndex & 0xFF);
			wlen += 2;
			System.arraycopy(keyName.getBytes(), 0, bSecBufferIn, wlen, 8);
			wlen += 8;

			byte[] tmpPin = new byte[1024];
			if(Str2Hex(strInPassword.getBytes(),tmpPin,pwdLen) != true){
				return HsmConst.EPARA_INPUT_DATA;
			}
			pwdLen = pwdLen/2;
			bSecBufferIn[wlen] = (byte)(pwdLen >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(pwdLen & 0xFF);
			wlen += 2;
			System.arraycopy(tmpPin, 0, bSecBufferIn, wlen, pwdLen);
			wlen += pwdLen;

			bSecBufferIn[wlen] = (byte)(0x01);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;

			iSndLen = wlen;

			wlen -= 2;
			bSecBufferIn[0] = (byte)((wlen >> 8) & 0xFF);
			bSecBufferIn[1] = (byte)(wlen & 0xFF);
		}else{
			bSecBufferIn[wlen] = (byte)(pwdLen >> 8 & 0xFF);
			bSecBufferIn[wlen+1] = (byte)(pwdLen & 0xFF);
			wlen += 2;
			System.arraycopy(strInPassword.getBytes(), 0, bSecBufferIn, wlen, pwdLen);
			wlen += pwdLen;
			bSecBufferIn[wlen] = (byte)(0x01);
			bSecBufferIn[wlen+1] = algFlag;
			wlen += 2;
			iSndLen = wlen;
			wlen -= 2;
			bSecBufferIn[0] = (byte)((wlen >> 8) & 0xFF);
			bSecBufferIn[1] = (byte)(wlen & 0xFF);
		}

		nResult = HSM_LINK(hSession,bSecBufferIn,iSndLen,bSecBufferOut);
		if(nResult != HsmConst.T_SUCCESS){
			return nResult;
		}

		outLen[0] = bSecBufferOut[3];
		System.arraycopy(bSecBufferOut, 4, CipherPwd, 0, outLen[0]);

		return HsmConst.T_SUCCESS;
	}
}
