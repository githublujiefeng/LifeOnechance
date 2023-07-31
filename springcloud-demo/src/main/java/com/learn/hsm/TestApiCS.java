package com.learn.hsm;

import com.learn.hsm.hsmj.*;

public class TestApiCS extends Thread {
	static long lValue = 0;
	static long lFailTimes = 0;

	static byte[] pinblock = new byte[25];
	static byte[] pin = new byte[13];
	static byte[] pinout = new byte[25];

	static String acco = "2001039807000001";

	String pwd = "534189";

	static byte[] cipherpwd = new byte[128];

	static HsmApp hApp = new HsmApp();
	static HsmSession hSession = new HsmSession("hsm.ini");

	int testEncryptPIN() {
		String pwd = "123456";

		byte[] pin = new byte[13];
		byte[] pinblock = new byte[24];

		System.arraycopy(pwd.getBytes(), 0, pin, 0, 6);
		int nRet = hApp.EncryptPIN(hSession, pin, pinblock);
		HsmApp.OutputDataHex( "testEncryptPIN("+new String(pin)+") ret=" + String.format("0x%x",nRet), pinblock, 24);

		return nRet;
	}

	void testGenerateMAC() {
		int nRet;
		byte [] data = new byte[128];
		byte[] mac = new byte[17];

		nRet = hApp.GenerateMAC(hSession, 128, data, mac);
		if (nRet != 0) {
			System.out.println("Generate MAC Failure! ErrorCode:" + nRet);
		}

		HsmApp.OutputDataHex("MAC of 128 bytes 0:",mac,16);
	}

	void testEncryptPassword() {

		int nRet = hApp.EncryptPassword(hSession, acco, pwd, cipherpwd);
		if (nRet != 0) {
		}
		System.out.println(new String(cipherpwd));
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

		if (HsmApp.printlog)
			System.out.println( "=====================  SM2 -> 3DES" );
		keyFlag=0x02;
		algFlag = 0x01;
		strInPassword = "12efc8ff855b598e97e433b9e611d01304eeff26bfcc2101a937f83fa54efe5d38a2e5f401395a694eb6bed6dd31adedc6ad86bbf274af3f76ddfe4085b779a0db04184ab90d4fad7a5b27bf43a0493e53b89461a2bc23db66cf116449677e1a24a9201f31";
		algFlag = 0x01;
		nRet = hApp.TransPwdSm2NX(hSession, keyFlag,channelIdx,channelName,strInPassword, algFlag,outlen, strCiphOut);
		if(nRet != 0){
			//incFailTimes();
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return", strCiphOut, outlen[0]);

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

		System.out.println( "=====================  TEXT -> 3DES" );
		keyFlag = 0x00;
		algFlag = 0x01;
		strInPassword = "534189";
		acco = "5341895341834189";

		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);


		System.out.println( "=====================  TEXT -> SM4" );
		keyFlag = 0x00;
		algFlag = 0x02;
		strInPassword = "0123456789abcdef12341234";

		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);


		System.out.println( "=====================  RSA -> 3DES" );
		keyFlag = 0x01;
		algFlag = 0x01;
		strInPassword = "3365A888107A84668A996559285FDFD14A7D5CB076AA2701E20AC1C56654B3830A5C4F7619538AD3374B78FF7CBB4795F483BDB8AD72226CAE16BD60A0D4E57DEF61AADA3F986831515351DE064627C29CBFE632F7685B02E3F1A6C4F451F5F9D70F3882575ECC7C347E30FC0664CC1D301ECB02E775A089523A5AB730B429EB";
		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);


		System.out.println( "=====================  RSA -> SM4" );
		keyFlag = 0x01;
		algFlag = 0x02;
		strInPassword = "3365A888107A84668A996559285FDFD14A7D5CB076AA2701E20AC1C56654B3830A5C4F7619538AD3374B78FF7CBB4795F483BDB8AD72226CAE16BD60A0D4E57DEF61AADA3F986831515351DE064627C29CBFE632F7685B02E3F1A6C4F451F5F9D70F3882575ECC7C347E30FC0664CC1D301ECB02E775A089523A5AB730B429EB";
		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);


		System.out.println( "=====================  SM2 -> 3DES" );
		keyFlag = 0x02;
		algFlag = 0x01;
		strInPassword ="D195FFF52BD4FB3D3703D60C6E480209AA25594A3923ED5D31DD2808CA0EA6CB8556A10E101019F7B75E972E3274F4EBB0D518E4BCBFD5B0A624B582A18F31EB88134934F52565BAE57517DFA4DB7A931BD73E10DB35E9078B36581F5B63785C840E73801AA3";
		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);

		System.out.println( "=====================  SM2 -> SM4" );
		keyFlag = 0x02;
		algFlag = 0x02;
		strInPassword ="D195FFF52BD4FB3D3703D60C6E480209AA25594A3923ED5D31DD2808CA0EA6CB8556A10E101019F7B75E972E3274F4EBB0D518E4BCBFD5B0A624B582A18F31EB88134934F52565BAE57517DFA4DB7A931BD73E10DB35E9078B36581F5B63785C840E73801AA3";
		nRet = hApp.TranPasswordSm2(hSession, keyFlag,channelIdx,channelName,acco,strInPassword,algFlag,outlen,strCiphOut);
		if(nRet != 0){
			System.out.println("TranPasswordSm2 Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("TranPasswordSm2 return succ", strCiphOut, outlen[0]);
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
			System.out.println("testTransPwdSm2NX Failure! ErrorCode:" + nRet);
		}
		else
			HsmApp.OutputDataHex("testTransPwdSm2NX return succ", strCipherPwd.getBytes(), strCipherPwd.length());
	}

	public void testall() {

		TestApiCS hTest = new TestApiCS();

		System.out.println("=====================testEncryptPIN()");
		hTest.testEncryptPIN();
		System.out.println("=====================testGenerateMAC()");
		hTest.testGenerateMAC();
		System.out.println("=====================testEncPwd()");
		hTest.testEncryptPassword();
		System.out.println("=====================testTranPasswordSm2()");
	}

	public static void main(String[] args) {
		new TestApiCS().testall();
	}
}