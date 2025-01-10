package com.example.start;

import org.platformlambda.core.annotations.MainApplication;
import org.platformlambda.core.models.EntryPoint;
import org.platformlambda.core.system.AutoStart;
import org.platformlambda.core.util.CryptoApi;
import org.platformlambda.core.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


@MainApplication
public class PersonServiceApplication implements EntryPoint {
	private static final Logger log = LoggerFactory.getLogger(PersonServiceApplication.class);
	private static final Utility util = Utility.getInstance();
	private static final CryptoApi crypto = new CryptoApi();

	private static final String TEMP_KEY_STORE = "/tmp/keystore";
	private static final String DEMO_MASTER_KEY = "demo.txt";

	public static void main(String[] args) {
		AutoStart.main(args);
	}

	public void start(String[] args) {
		// Create a demo encryption key if not exists
		File folder = new File(TEMP_KEY_STORE);
		if (!folder.exists()) {
			if (folder.mkdirs()) {
				log.info("Folder {} created", folder);
			}
		}
		File f = new File(folder, DEMO_MASTER_KEY);
		if (!f.exists()) {
			String b64Key = util.bytesToBase64(crypto.generateAesKey(256));
			util.str2file(f, b64Key);
			log.info("Demo encryption key {} created", f.getPath());
		}
		log.info("Started");
	}

}
