In this program, we want to encrypt and decrypt a file or folder using AES or DES algorithm. 

First of all, the program asks the user to choose between (1) encrypt or (2) decrypt or (3) exit the program. 

then the program asks the user to choose between (1) a folder or (2) a file and then enter the name of the file or the folder
after that, the user selects the algorithm AES or DES. 

If the user choose AES, the program asks the user to enter the key, which must be 192 bit; otherwise, the program
won't complete the decrypt or encrypt process. 

If the user chooses DES, the program asks the user to enter the key, which must be 64 bit; otherwise, the program
won't complete the decrypt or encrypt process.

If the user enters the key correctly, the program procced to encrypt or decrypt the file or folder. 
Ameera Abdullah Almohammdi 3752744
Sana Mohammed Bassam Garah 3752533

In detail, suppose the user wants to (1) encrypt a (1) folder named "text".
First, the user enters the path of the folder, for example: "C:\Users\user\Downloads\Security\text\\".
Then chooses the algorithm to use. If it was AES, then the user enters the key, which must be a 192 bit (approximately 24 letters).
for example: "abcdefghjklmnopqrstuvhtv".
Hint (Only the current versions of jdk accepts 192 bit, old versions need to download the policy to allow that use).

The program now will execute the encrypt method ---> "encrypt(C:\Users\user\Downloads\Security\text\\, "abcdefghjklmnopqrstuvhtv", AES, 1)"
according to file type (1) folder and (2) file, there is a two separate process in this case;
if it is a folder the program will bring all the file in the folder to an array,
then according to the algorithm used, the program will continue the encryption method by reading what inside the files,
then start using the encryption of the AES algorithm,
then printing the text after encryption,
and finally writing the encrypted files.

If the user wants to decrypt a folder, the program will bring all the encrypted files in the folder to an array 
then according to the algorithm used, the program will continue the decryption method by reading what inside the files,
then start using the decryption of the AES algorithm,
then printing the text after decryption,
and finally writing the decrypted files.

If the user wants to (1) encrypt a (2) file named "text".
First, the user enters the path of the file, for example: "C:\Users\user\Downloads\Security\text.txt".
Then chooses the algorithm to use. If it was DES, then the user enters the key, which must be a 64 bit (multiple of 8 letters).
for example: "abcdefgh".
The program will excute the encrypt method ---> "encrypt(C:\Users\user\Downloads\Security\text.txt, "abcdefgh", DES, 2)",
then continue as described previously.

for extra details for the functionality of code, review the code comments.


In order to compile run the program, you preferly need to have the new vesrion of jdk, we used jdk 15.0.1, and Apache NetBeans 12.1 to test and run the program.
