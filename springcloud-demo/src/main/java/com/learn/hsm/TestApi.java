package com.learn.hsm;

import com.learn.hsm.hsmj.*;

public class TestApi extends Thread {
	static long lValue = 0;
	static long lFailTimes = 0;

	static byte[] pinblock = new byte[25];
	static byte[] pin = new byte[13];
	static byte[] pinout = new byte[25];

//	static String acco = "201605170000045678";
	static String acco = "2001039807000001";

//	static String pwd = "abcd@ef&12343gh";
//	String pwd = "A11111";
	String pwd = "534189";

	static byte[] cipherpwd = new byte[50];

	static HsmApp hApp = new HsmApp();
	static HsmSession hSession = new HsmSession("hsm.ini");

	static private synchronized void incValue() {
		++lValue;
	}

	static private synchronized void incFailTimes() {
		++lFailTimes;
	}


	int testEncryptPIN() {
		byte[] pinblock = new byte[24];
		
		HsmApp.printlog=true;

		System.arraycopy("123456".getBytes(), 0, pin, 0, 6);
		int nRet = hApp.EncryptPIN(hSession, pin, pinblock);
		HsmApp.OutputDataHex( "pin", pin, 12 );
		HsmApp.OutputDataHex( "testEncryptPIN("+new String(pin)+") ret=" + String.format("0x%x",nRet), pinblock, 24);

		return nRet;
	}

	
	void testConvertPIN() {
		int nRet;

		incValue();

		nRet = hApp.ConvertPIN(hSession, pinblock, pinout);

		if (nRet != 0) {
			incFailTimes();
			System.out.println("testConvertPIN() return:" + nRet);
		} 

		//		HsmApp.OutputDataHex("PIN 1",pinblock,24);
		//		HsmApp.OutputDataHex("PIN 2",pinout,24);
	}

	
	void testGenerateMAC() {
		int nRet;
		byte [] data = new byte[128];
		byte[] mac = new byte[17];

		incValue();

		//System.arraycopy("123456ab--------".getBytes(), 0, data, 0, 16);

		nRet = hApp.GenerateMAC(hSession, 128, data, mac);
		if (nRet != 0) {
			incFailTimes();

			System.out.println("Generate MAC Failure! ErrorCode:" + nRet);
		}

		HsmApp.OutputDataHex("MAC of 128 bytes 0:",mac,16);
	}

	
	void testEncryptPassword() {

		incValue();

		acco = "001000478881";
		pwd= "A11111";
		int nRet = hApp.EncryptPassword(hSession, acco, pwd, cipherpwd);
		if (nRet != 0) {
			incFailTimes();
		}

		System.out.println(new String(cipherpwd));
	}

	
	void testVerifyPassword() {

		incValue();

		String strCiph = new String(cipherpwd).trim();
		int nRet = hApp.VerifyPassword(hSession, acco, pwd, strCiph);
		if (nRet != 0) {
			incFailTimes();
			//			System.out.println("VerifyPassword Failure! ErrorCode:" + nRet);
		} else {
			//			System.out.println("VerifyPassword SUCEED!");
		}
	}

	
	void testTransPwdWY2NF()
	{
		int nRet;
		String strCiph = new String(cipherpwd).trim();
		byte[] strSiphOut = new byte[50];
		nRet = hApp.TransPwdWY2NF(hSession,acco,strCiph, acco, strSiphOut);
		if(nRet != 0){
			incFailTimes();
			System.out.println("TransPwdWY2NF Failure! ErrorCode:" + nRet);
		}
		else
			System.out.println("TransPwdWY2NF return" + new String(strSiphOut));
	}
	

	void testTransPwdHX2NF()
	{
		int nRet;
		//			String strCiph = new String(cipherpwd).trim();
		byte[] strCiphOut = new byte[50];
		nRet = hApp.TransPwdHX2NF(hSession, pinblock, acco, strCiphOut);
		if(nRet != 0){
			incFailTimes();
			System.out.println("TransPwdHX2NF Failure! ErrorCode:" + nRet);
		}
		else
			System.out.println("TransPwdHX2NF return" + new String(strCiphOut));
	}

	
	void testTransPwdNF2HX()
	{
		int nRet;
		String strCiph = new String(cipherpwd).trim();
		byte[] strCiphOut = new byte[50];
		nRet = hApp.TransPwdNF2HX(hSession,acco,strCiph, strCiphOut);
		if(nRet != 0){
			incFailTimes();
			System.out.println("TransPwdWY2NF Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TransPwdNF2HX return", strCiphOut, 24);
	}

	
	void testTransPwdSm2NX()
	{
		byte keyFlag;  /* 0-text  1-RSA  2-SM2 */
		int  channelIdx = 1;
		String channelName = "11111111";
		String strInPassword ="";
		byte algFlag; /* 1-3DES  2-SM4 */

		int[] outlen = new int[2];
		byte[] strCiphOut = new byte[50];
		int nRet;

		if (HsmApp.printlog)
			System.out.println( "=====================  TEXT -> 3DES" );
		keyFlag = 0x00;
		algFlag = 0x01;
		strInPassword = "123456";
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);
		incValue();

		if (HsmApp.printlog)
			System.out.println( "=====================  TEXT -> SM4" );
		keyFlag=0x00;
		algFlag = 0x02;
		strInPassword = "123456";
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);
		incValue();

		if (HsmApp.printlog)
			System.out.println( "=====================  RSA -> 3DES" );
		keyFlag=0x01;
		algFlag = 0x01;
		strInPassword = "36B2E89E7DA52369C2CA1523359605FBEBC43D675B21ABCDD1E382D6A84AEAA4D396425A80E995E45F7206C16322292D618996DB380875B33C1CAEB8DCD0206891A73EC4F7A67CB69983385E57779DDD7CACA576FC06084BE5CF7FC6BD9F2750A1A4804C7671FB3ADED4A64DEACD540465A38E7F3621015585F0FCD04949385D";
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			//incFailTimes();
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);
		incValue();

		if (HsmApp.printlog)
			System.out.println( "=====================  RSA -> SM4" );
		keyFlag=0x01;
		algFlag = 0x02;
		strInPassword = "3365A888107A84668A996559285FDFD14A7D5CB076AA2701E20AC1C56654B3830A5C4F7619538AD3374B78FF7CBB4795F483BDB8AD72226CAE16BD60A0D4E57DEF61AADA3F986831515351DE064627C29CBFE632F7685B02E3F1A6C4F451F5F9D70F3882575ECC7C347E30FC0664CC1D301ECB02E775A089523A5AB730B429EB";
		algFlag = 0x02;
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			//incFailTimes();
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);
		incValue();

		if (HsmApp.printlog)
			System.out.println( "=====================  SM2 -> 3DES" );
		keyFlag=0x02;
		algFlag = 0x01;
		strInPassword = "12efc8ff855b598e97e433b9e611d01304eeff26bfcc2101a937f83fa54efe5d38a2e5f401395a694eb6bed6dd31adedc60cad86bbf274af3f76ddfe4085b779a0db04184ab90d4fad7a5b27bf43a0493e53b89461a2bc23db66cf116449677e1a24a9201f31";
		algFlag = 0x01;
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			//incFailTimes();
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);
		incValue();

		if (HsmApp.printlog)
			System.out.println( "=====================  SM2 -> SM4" );
		keyFlag=0x02;
		algFlag = 0x02;
		strInPassword = "12efc8ff855b598e97e433b9e611d01304eeff26bfcc2101a937f83fa54efe5d38a2e5f401395a694eb6bed6dd31adedc60cad86bbf274af3f76ddfe4085b779a0db04184ab90d4fad7a5b27bf43a0493e53b89461a2bc23db66cf116449677e1a24a9201f31";
		algFlag = 0x02;
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			//incFailTimes();
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);
	}

	
	void testTranPasswordSm2()
	{
		byte keyFlag;  /* 0-text 1-rsa 2-sm2 */
		int  channelIdx = 1;
		String channelName = "11111111";
		String strInPassword ="";
		byte algFlag;  /* 1-3des 2-sm4 */
		int[] outlen = new int[2];
		byte[] strCiphOut = new byte[128];
		int nRet;

		incValue();

//		System.out.println( "=====================  TEXT -> 3DES" );
//		keyFlag = 0x00;
//		algFlag = 0x01;
//		strInPassword = "534189";
//		acco = "534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189534189";
//
//		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
//		if(nRet != 0){
//			incFailTimes();
//			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
//		}
//		else
//			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);
//
//		System.exit(0);
//		incValue();

		System.out.println( "=====================  TEXT -> SM4" );
		keyFlag = 0x00;
		algFlag = 0x02;
		strInPassword = "0123456789abcdef12341234";


		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			incFailTimes();
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);

		incValue();

//		System.out.println( "=====================  RSA -> 3DES" );
//		keyFlag = 0x01;
//		algFlag = 0x01;
//		strInPassword = "3365A888107A84668A996559285FDFD14A7D5CB076AA2701E20AC1C56654B3830A5C4F7619538AD3374B78FF7CBB4795F483BDB8AD72226CAE16BD60A0D4E57DEF61AADA3F986831515351DE064627C29CBFE632F7685B02E3F1A6C4F451F5F9D70F3882575ECC7C347E30FC0664CC1D301ECB02E775A089523A5AB730B429EB";
//		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
//		if(nRet != 0){
//			incFailTimes();
//			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
//		}
//		else
//			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);
//
//		incValue();
//
//		System.out.println( "=====================  RSA -> SM4" );
//		keyFlag = 0x01;
//		algFlag = 0x02;
//		strInPassword = "3365A888107A84668A996559285FDFD14A7D5CB076AA2701E20AC1C56654B3830A5C4F7619538AD3374B78FF7CBB4795F483BDB8AD72226CAE16BD60A0D4E57DEF61AADA3F986831515351DE064627C29CBFE632F7685B02E3F1A6C4F451F5F9D70F3882575ECC7C347E30FC0664CC1D301ECB02E775A089523A5AB730B429EB";
//		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
//		if(nRet != 0){
//			incFailTimes();
//			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
//		}
//		else
//			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);
//
//		incValue();
//
//		System.out.println( "=====================  SM2 -> 3DES" );
//		keyFlag = 0x02;
//		algFlag = 0x01;
//		strInPassword ="D195FFF52BD4FB3D3703D60C6E480209AA25594A3923ED5D31DD2808CA0EA6CB8556A10E101019F7B75E972E3274F4EBB0D518E4BCBFD5B0A624B582A18F31EB88134934F52565BAE57517DFA4DB7A931BD73E10DB35E9078B36581F5B63785C840E73801AA3";
//		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
//		if(nRet != 0){
//			incFailTimes();
//			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
//		}
//		else
//			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);
//
//		incValue();
//
//		System.out.println( "=====================  SM2 -> SM4" );
//		keyFlag = 0x02;
//		algFlag = 0x02;
//		strInPassword ="D195FFF52BD4FB3D3703D60C6E480209AA25594A3923ED5D31DD2808CA0EA6CB8556A10E101019F7B75E972E3274F4EBB0D518E4BCBFD5B0A624B582A18F31EB88134934F52565BAE57517DFA4DB7A931BD73E10DB35E9078B36581F5B63785C840E73801AA3";
//		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
//		if(nRet != 0){
//			incFailTimes();
//			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
//		}
//		else
//			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);
	}


	void testVerifyWYPasswordSm2()
	{
		byte keyFlag = 0x02;
		int  channelIdx = 1;
		String channelName = "11111111";
		String strInPassword ="D195FFF52BD4FB3D3703D60C6E480209AA25594A3923ED5D31DD2808CA0EA6CB8556A10E101019F7B75E972E3274F4EBB0D518E4BCBFD5B0A624B582A18F31EB88134934F52565BAE57517DFA4DB7A931BD73E10DB35E9078B36581F5B63785C840E73801AA3";
		byte algFlag = 0x02;
		String strCipherPwd = "C1F37C3827784784";

		int nRet = hApp.VerifyWYPasswordSm2(hSession,keyFlag,channelIdx,channelName, acco, strInPassword, algFlag,strCipherPwd);
		if(nRet != 0){
			incFailTimes();
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return succ", strCipherPwd.getBytes(), strCipherPwd.length());
	}

	
	public void run() {
		while (true) {
			//			testEncryptPIN();
			//			testTranPasswordSm2();
			//			testVerifyPwd();
			testTransPwdSm2NX();
			//			try { sleep(100); } catch (Exception e) {};
		}
	}

	
	public void testall() {
		HsmApp.printlog = true;

		TestApi hTest = new TestApi();

		//		System.out.println("=====================testEncryptPIN()");
		//		hTest.testEncryptPIN();
		//		System.out.println("=====================testConvertPIN()");
		//		hTest.testConvertPIN();
		//		System.out.println("=====================testGenerateMAC()");
		//		hTest.testGenerateMAC();
		//		System.out.println("=====================testEncPwd()");
		//		hTest.testEncryptPassword();
		//		System.out.println("=====================testVerifyPwd()");
		//		hTest.testVerifyPassword();
		//		System.out.println("=====================testTransPwdWY2NF()??");
		//		hTest.testTransPwdWY2NF();
		//		System.out.println("=====================testTransPwdHX2NF()");
		//		hTest.testTransPwdHX2NF();
		//		System.out.println("=====================testTransPwdNF2HX()??");
		//		hTest.testTransPwdNF2HX();
		System.out.println("=====================testTransPwdSm2NX()");
		hTest.testTransPwdSm2NX();
		//		System.out.println("=====================testTranPasswordSm2()");
		//		hTest.testTranPasswordSm2();
		//		System.out.println("=====================testVerifyWYPasswordSm2()");
		//		hTest.testVerifyWYPasswordSm2();

		System.exit(0);
	}

	public void pressall() {

		HsmApp.printlog = false;
		new TestMonitor().start();

		for ( int i=0; i<4; i++) {
			new TestApi().start();
			try { sleep(50); } catch (Exception e) {};
		}
	}

	public static void main(String[] args) {

//				new TestApi().testEncryptPIN();
				new TestApi().testEncryptPassword();
				System.exit(0);

		//		new TestApi().testEncryptPassword();
//				new TestApi().testTranPasswordSm2();
//				System.exit(0);

		//		new TestMonitor().start();

		//		// ��������ѭ��
		//		TestApi ta = new TestApi();
		//		HsmApp.printlog = false;
		//		for ( int i=0; ; i++ )
		//		{
		//			ta.testEncryptPIN();
		//		    if ( i%10000==0 )
		//		    {
		//		    	System.out.print(".");
		//		    }
		//		    if ( i%1000000==0 )
		//		    {
		//		    	System.out.println(".");
		//		    }

		//			try {
		//				sleep(5000);
		//			} catch (InterruptedException e) {
		//				e.printStackTrace();
		//			}
		//		}

		//		for ( int i=0;i<30; i++ )
		//		{
		//			ta.testEncryptPIN();
		//			System.out.print(".");
		//		}
		//		System.exit(0);


		//		new TestApi().testall();
		//		System.exit(0);

//		new TestApi().pressall();

		//		HsmApp.printlog = false;
		//		TestApi t = new TestApi();
		//		for ( int i=0;i<500; i++ )
		//		{
		//		t.testTransPwdSm2NX();
		//		}
		//		System.exit(0);
		
		
				HsmApp.printlog= false;
				TestApi t = new TestApi();
				t.testTranPasswordSm2();

		//		System.out.println("=====================testEncryptPIN()");
		//		t.testEncryptPIN();
		//		System.out.println("=====================testEncryptPassword()");
		//		t.testEncryptPassword();
		//		System.out.println("=====================testVerifyPassword()");
		//		t.testVerifyPassword();
		//		System.out.println("=====================testVerifyWYPasswordSm2()");
		//		t.testVerifyWYPasswordSm2();
				System.out.println("=====================testTranPasswordSm2()");
				t.testTranPasswordSm2();
				System.out.println("=====================testTransPwdSm2NX()");
				t.testTransPwdSm2NX();
				System.exit(0);

	}
}

class TestMonitor extends Thread {
	long speed;
	long preValue;

	public void run() {
		while (true) {
			try {
				preValue = TestApi.lValue;
				sleep(1000);
				speed = TestApi.lValue - preValue;
				System.out.println("Run: " + TestApi.lValue + " Speed: " + speed + " Fail: " + TestApi.lFailTimes);
			} catch (InterruptedException e) {
			}
		}
	}
}