package Security;


import java.security.Policy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    //The space between letters
    private static SecretKeySpec secretKey;
    //Array for the key
    private static byte[] key;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        //the input (1) encrypt (2) decrypt (3) exit
        int input;
        //the algorithm AES or DES
        String alog;
        //file name 
        String fileName;
        //the type (1) folder (2) file
        int typefile;
        // The key used to encrypt or decrypt
        String keyFileName;
        
        boolean running = true;
        
        // loop to keep the program running until "running = false"
        while (running) {
            System.out.println("1 - Encrypt");
            System.out.println("2 - Decrypt");
            System.out.println("3 - Exit");
            System.out.println("Select Option Number : ");
            input = sc.nextInt();
            switch (input) {

                case 1: {
                    System.out.println("Enter yor chouse (1) Folder (2) File : ");
                    typefile = sc.nextInt();
                    System.out.println("Enter File Name To Encrypt : ");
                    fileName = sc.next();
                    System.out.println("Enter Alog (AES , DES) : ");
                    alog = sc.next();
                    System.out.println("Enter Key Name : ");
                    keyFileName = sc.next();
                   //to save the key in a file so it can't be lost or forgotten
                    writeKey(keyFileName);
                    
                  //encrypt method 
                    encrypt(fileName, keyFileName, alog, typefile);
                    break;
                }
                case 2: {
                    System.out.println("Enter yor chouse (1) Folder (2) File : ");
                    typefile = sc.nextInt();
                    System.out.println("Enter File Name To Decrypt : ");
                    fileName = sc.next();
                    System.out.println("Enter Alog (AES , DES) : ");
                    alog = sc.next();
                    System.out.println("Enter Key Name : ");
                    keyFileName = sc.next();
                    //to save the key in a file so it can't be lost or forgotten 
                    writeKey(keyFileName);
                    decrypt(fileName, keyFileName, alog, typefile);
                    break;
                }
                case 3:
                    // to exit the program
                    running = false;
            }
            System.out.println("*******************************************");
        }

    }

    public static String encrypt(String fileName, String secret, String alog, int typeFile) {
         // to encrypt folder
        if (typeFile == 1) {
            //C:\Users\hp\Documents\NetBeansProjects\Security\text\\
            File folder = new File(fileName);
            //to give access to the folder
            folder.getParentFile().mkdirs();
            // bring all the file in the folder to an array
            File[] listOfFiles = folder.listFiles();
            // for each loop to access each file in the folder
            for (File file : listOfFiles) {
                //to make sure that the file is not empty
                if (file.isFile()) {
                    //if the algorithm is AES excute EncriptionFileAES method
                    if (alog.equalsIgnoreCase("AES")) {
                        //to encrypt all the files in the folder " file.getName()"
                        EncriptionFileAES(fileName + file.getName(), secret);
                    } 
                    // otherwise excute EncriptionFileDES method
                    else {
                        EncriptionFileDES(fileName + file.getName());
                    }
                }
            }
        } 
          // to encrypt only one file
        //C:\Users\hp\Documents\NetBeansProjects\Security\text.txt
        else {
            //if the algorithm is AES excute EncriptionFileAES method
            if (alog.equalsIgnoreCase("AES")) {
                EncriptionFileAES(fileName, secret);
            } 
            // otherwise excute EncriptionFileDES method
            else {
                EncriptionFileDES(fileName);

            }
        }
        return null;

    }

    public static String decrypt(String fileName, String secret, String alog, int typeFile) {
        // to decrypt folder
        if (typeFile == 1) {
            File folder = new File(fileName);
            //to give access to the folder
            folder.getParentFile().mkdirs();
            // bring all the file in the folder to an array
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                 //if the algorithm is AES excute DecriptionFileAES method
                    if (alog.equalsIgnoreCase("AES")) {
                        DecriptionFileAES(fileName + file.getName(), secret);

                    } 
                   // otherwise excute DecriptionFileDES method 
                    else {
                        DecriptionFileDES(fileName+file.getName());
                    }

                }
            }
        }
        //to decrypt  only one file
        else {

            if (alog.equalsIgnoreCase("AES")) {
                DecriptionFileAES(fileName, secret);

            } else {
                DecriptionFileDES(fileName);
             
            }
        }

        return null;
    }

            //encryption file using AES
    private static void EncriptionFileAES(String fileName, String secret) {
        //try and catch to deal with errors
        try {
            //read what inside the files, st is the string returned from the method readfile
            String st = ReadFile(fileName);
            setKey(secret);
            //start using the AES algorithm
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //to encrypt the bytes of the specified content and make it UTF-8
            String x = Base64.getEncoder().encodeToString(cipher.doFinal(st.getBytes("UTF-8")));
            // print the text after encryption
            System.err.println("Encription = " + x);
            //to wtire the ecnrypted file
            WriteFile(fileName, x, 0);

        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
    }
   // Decription file using AES 
    private static void DecriptionFileAES(String fileName, String secret) {
        try {
            //read what inside the files, st is the string returned from the method readfile
            String st = ReadFile(fileName);
            setKey(secret);
            //start using the AES algorithm
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
           //to decrypt the bytes of the specified content
            String x = new String(cipher.doFinal(Base64.getDecoder().decode(st)));
           // print the text after decryption
            System.err.println("deEncription = " + x);
            //to wtire the decrypted file
            WriteFile(fileName, x, 1);

        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
    }
       //encryption file using DES
    private static void EncriptionFileDES(String fileName) {
        //initial 8 values for the key
        byte[] initialization_vector = {22, 33, 11, 44, 55, 99, 66, 77};
        String encrypted = fileName + ".encripted";
        try {
            SecretKey secret_key = readKey(fileName, "DES");
            //to make sure the size of the key is correct
            AlgorithmParameterSpec alogrithm_specs = new IvParameterSpec(initialization_vector);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret_key, alogrithm_specs);
            //to read from the desired file and write encryption in new file
            encryptDES(new FileInputStream(fileName), new FileOutputStream(encrypted), cipher);
            System.out.println("End of Encryption procedure!");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    // Decription file using DES 
    private static void DecriptionFileDES(String fileName) {
        Cipher cipher;
        String original = fileName + ".decripted";
        String encrypted = fileName;
        //initial 8 values for the key
        byte[] initialization_vector = {22, 33, 11, 44, 55, 99, 66, 77};
        try {
            SecretKey secret_key = readKey(fileName, "DES");
            //to make sure the size of the key is correct
            AlgorithmParameterSpec alogrithm_specs = new IvParameterSpec(initialization_vector);
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret_key, alogrithm_specs);
            //to read from the desired file and write decryption in new file
            decryptDES(new FileInputStream(encrypted), new FileOutputStream(original), cipher);
            System.out.println("End of Decryption procedure!");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    private static void encryptDES(InputStream input, OutputStream output, Cipher encrypt) throws IOException {
        output = new CipherOutputStream(output, encrypt);
        writeBytes(input, output);
    }
    
    private static void decryptDES(InputStream input, OutputStream output, Cipher decrypt) throws IOException {

        input = new CipherInputStream(input, decrypt);
        writeBytes(input, output);
    }

    private static void writeBytes(InputStream input, OutputStream output) throws IOException {
        byte[] writeBuffer = new byte[1024];
        int readBytes = 0;
        while ((readBytes = input.read(writeBuffer)) >= 0) {
            output.write(writeBuffer, 0, readBytes);
        }
        output.close();
        input.close();
    }
    
    private static SecretKey readKey(String input, String algorithm) throws Exception {
        FileInputStream fis = new FileInputStream(input);
        //to make sure it's not empty
        int kl = fis.available();
        //to write the key in byte array
        byte[] kb = new byte[kl];

        fis.read(kb);
        //close to save memory
        fis.close();
        KeySpec ks = null;
        SecretKey ky = null;
        SecretKeyFactory kf = null;
        if (algorithm.equalsIgnoreCase("DES")) {
            File file = new File("filename.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                System.out.println(st);
                kb = st.getBytes("UTF-8");
                //to manage the spacing in the key
                ks = new DESKeySpec(kb);
                kf = SecretKeyFactory.getInstance("DES");
                ky = kf.generateSecret(ks);
            }

        }

        return ky;
    }
     //to save the key in a file so it can't be lost or forgot
    private static void writeKey(String output) throws Exception {
        FileWriter myWriter = new FileWriter("keyStore.txt");
        myWriter.write(output);
        //close to save memory
        myWriter.close();
    }

   
      
    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            //to return the bytes from the key in UTF-8 system to make it understandable
            key = myKey.getBytes("UTF-8");
            // embedded algorithm to make the key correct 
            sha = MessageDigest.getInstance("SHA-1");
            //to ensure that the key is correct
            key = sha.digest(key);
           //to take copy of the first 24 letter of the key 
            key = Arrays.copyOf(key, 24);
            secretKey = new SecretKeySpec(key, "AES");
            
        //to print any error related to the key encounter during the run of the program related to the key
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
        //read what inside the files
    private static String ReadFile(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        //read all the lines in the file
        while ((st = br.readLine()) != null) {
            System.out.println(st);
            //return all the lines in the file
            return st;
        }
        return "s";
    }
    
      //to wtire the ecnrypted file
    private static void WriteFile(String fileName, String x, int num) throws IOException {
        FileWriter myWriter;
        if (num == 1) {
            myWriter = new FileWriter(fileName + ".decrpted");
           //write the decrypted text inside the file
            myWriter.write(x);
            myWriter.close();
        } else {
            // create new file
            myWriter = new FileWriter(fileName + ".encrpted");
          //write the encrypted text inside the file
            myWriter.write(x);
            myWriter.close();
        }
    }
    
}
