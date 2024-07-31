package com.learn.hsm.hsmj;

public class HsmConst {
	public static final int SECBUF_MAX_SIZE = 2048;
	public static final int MONI_INTERVAL	= 10000;
	public static final int TEST_INTERVAL = 360; // 360 * 10000 minisecond = 1 hour

	public static final int T_SUCCESS       = 0;
	public static final int T_FAIL          = 1;

	/*****************Error Code****************/
	public static final int ERR_CONFIG_FILE  = 0x90;
	public static final int ERR_CONNECT_HSM  = 0x91;
	public static final int ERR_SENDTO_HSM   = 0x92;
	public static final int ERR_RECVFORM_HSM = 0x93;
	public static final int ERR_SESSION_END  = 0x94;
	public static final int ERR_HANDLE_FAULT = 0x95;

	public static final byte KEY_ZMK	=		0x01;
	public static final byte KEY_TMK 	=		0x02;	
	public static final byte KEY_PIK 	=		0x11;
	public static final byte KEY_MAK 	=		0x12;
	public static final byte KEY_DTK 	=		0x13;
	public static final byte KEY_CVK 	=		0x21;

	public static final byte MAC_XOR 		=(byte)01;
	public static final byte MAC_ANSI99 	=(byte)02;
	public static final byte MAC_ANSI919	=(byte)03;

	public static final byte PIN_ANXI98A	=(byte)01;
	public static final byte PIN_DOCUTE1	=(byte)02;
	public static final byte PIN_IBM		=(byte)03;
	public static final byte PIN_PLUS		=(byte)04;
	public static final byte PIN_ISO9564	=(byte)05;
	public static final byte PIN_ANXI98	=(byte)06;

	public static final byte EKEY_NO_MKEY	= (byte)0x01;
	public static final byte EKEY_NO_BMKEY	= (byte)0x02;
	public static final byte EDES_NO_MODE	= (byte)0x10;
	public static final byte EKEY_INVALID_BMK_INDEX = (byte)0x0C;
	public static final byte ECHK_VALUE	= (byte)0x81;
	public static final byte EASC_DATA_LEN = (byte)0x68;
	public static final byte ECMD_INDATA	=	(byte)0x46;

	public static final int  RSA_MODE_LEN  		= 	0xb0;    
	public static final int  RSA_DATA_LEN  		=   0xb1;    
	public static final int  RSA_PUB_DECODE		=	0xb2;    
	public static final int  RSA_PUB_ENC   		=  	0xb3;   
	public static final int  RSA_INDEX_OUTBOUND	=	0xb4; 
	public static final int  RSA_GEN_KEY		=	0xb5;
	public static final int  RSA_PUB_ENCODE		=	0xb6;
	public static final int  RSA_PUB_DEC		=	0xb7;
	public static final int  RSA_PRI_STORAGE	=	0xb8;
	public static final int  RSA_ALOG_ERR      	=	0xb9;
	public static final int  RSA_PRI_NOTEXIST  	=	0xba;
	public static final int  RSA_PRI_ENC  		=	0xbb;
	public static final int  RSA_PRI_DECODE		=	0xbc;
	public static final int  RSA_VER_SIGN   	=   0xbd;
	public static final int  RSA_PRI_DEC  		=	0xbe;

	/*Define Value*/	
	public static final int EKEY_LENGTH			= 32;
	public static final int MAX_ZMK_INDEX 		= 99;
	public static final int MAX_RSAKEY_INDEX	= 49;

	public static final int EPIN_LENGTH			= 0x24;
	public static final int EMES_TOO_SHORT      = 0x61;
	public static final int EMES_TOO_LONG       = 0x62;
	public static final int EPARA_INPUT_DATA    = 0x63;
	public static final int EPIN_VERIFY         = 0x1E;

}