package ExampleSetup.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.google.protobuf.ByteString;
import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.FileAppendTransaction;
import com.hedera.hashgraph.sdk.FileContentsQuery;
import com.hedera.hashgraph.sdk.FileCreateTransaction;
import com.hedera.hashgraph.sdk.FileId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TokenCreateTransaction;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenInfoQuery;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransferTransaction;

import io.github.cdimascio.dotenv.Dotenv;

public class studentTokens {
	
	//Method that encodes NFT images to Base64 so that they can be uploaded as a Hedera file
	private static String encodeFileToBase64(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        Base64.Encoder encoder = Base64.getEncoder();
        fileInputStreamReader.read(bytes);
        return new String(encoder.encodeToString(bytes));
    }
	
	private static void chooseSTEM(FileId peerfile, Client opclient, PrivateKey imagekey, Scanner clubinput) throws Exception {
		//Adding STEM image to student's NFT file
		 File STEMimage = new File("C:\\Users\\pastr\\OneDrive\\Desktop\\stemtoken.jpg");
	        
	     String encodedSTEMimage = (" " + encodeFileToBase64(STEMimage));
	     
	     FileAppendTransaction appendSTEM = new FileAppendTransaction()
				    .setFileId(peerfile)
				    .setContents("\n" + encodedSTEMimage);
			FileAppendTransaction modifyMaxTransactionFee = appendSTEM.setMaxTransactionFee(new Hbar(2)); 

			
			TransactionResponse txResponse = modifyMaxTransactionFee.
					freezeWith(opclient)
					.sign(imagekey)
					.execute(opclient);

			
			TransactionReceipt receipt = txResponse.getReceipt(opclient);

			
			Status transactionStatus = receipt.status;

			System.out.println("The transaction consensus status is " + transactionStatus + ". You are a part of STEM Team!");
            System.out.println("Type 0 to display your NFT (encoded in Base64, use a decoder online to display images)");
            System.out.println("Type 1 for ICE");
            System.out.println("Type 2 for Robotics Club");
            System.out.println("Type 3 for P2P Tutoring");
            int choice = clubinput.nextInt();
            
            if(choice == 0) {
            	FileContentsQuery query = new FileContentsQuery()
            		    .setFileId(peerfile);

            		//Sign with client operator private key and submit the query to a Hedera network
            		ByteString contents = query.execute(opclient);

            		//Change to Utf-8 encoding
            		String contentsToUtf8 = contents.toStringUtf8();

            		System.out.println(contentsToUtf8);
            }
            else if(choice == 1) {
            	chooseICE(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 2) {
            	chooseRobot(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 3) {
            	chooseP2P(peerfile, opclient, imagekey, clubinput);
            }
	}
	
	private static void chooseICE(FileId peerfile, Client opclient, PrivateKey imagekey, Scanner clubinput) throws Exception {
		//Adding ICE image to student's NFT file
		 File ICEimage = new File("C:\\Users\\pastr\\OneDrive\\Desktop\\icetoken.jpg");
	        
	     String encodedICEimage = (" " + encodeFileToBase64(ICEimage));
	     
	     FileAppendTransaction appendICE = new FileAppendTransaction()
				    .setFileId(peerfile)
				    .setContents("\n" + encodedICEimage);
			FileAppendTransaction modifyMaxTransactionFee = appendICE.setMaxTransactionFee(new Hbar(2)); 

			
			TransactionResponse txResponse = modifyMaxTransactionFee.
					freezeWith(opclient)
					.sign(imagekey)
					.execute(opclient);

			
			TransactionReceipt receipt = txResponse.getReceipt(opclient);

			
			Status transactionStatus = receipt.status;

			System.out.println("The transaction consensus status is " + transactionStatus + ". You are a part of ICE!");
			System.out.println("Type 0 to display your NFT (encoded in Base64, use a decoder online to display images)");
            System.out.println("Type 1 for STEM");
            System.out.println("Type 2 for Robotics Club");
            System.out.println("Type 3 for P2P Tutoring");
            int choice = clubinput.nextInt();
            
            if(choice == 0) {
            	FileContentsQuery query = new FileContentsQuery()
            		    .setFileId(peerfile);

            		//Sign with client operator private key and submit the query to a Hedera network
            		ByteString contents = query.execute(opclient);

            		//Change to Utf-8 encoding
            		String contentsToUtf8 = contents.toStringUtf8();

            		System.out.println(contentsToUtf8);
            }
            else if(choice == 1) {
            	chooseSTEM(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 2) {
            	chooseRobot(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 3) {
            	chooseP2P(peerfile, opclient, imagekey, clubinput);
            }
	}
	
	private static void chooseRobot(FileId peerfile, Client opclient, PrivateKey imagekey, Scanner clubinput) throws Exception {
		//Adding robotics image to student's NFT file
		 File Robotimage = new File("C:\\Users\\pastr\\OneDrive\\Desktop\\robottoken.jpg");
	        
	     String encodedRobotimage = (" " + encodeFileToBase64(Robotimage));
	     
	     FileAppendTransaction appendRobot = new FileAppendTransaction()
				    .setFileId(peerfile)
				    .setContents("\n" + encodedRobotimage);
			FileAppendTransaction modifyMaxTransactionFee = appendRobot.setMaxTransactionFee(new Hbar(2)); 

			
			TransactionResponse txResponse = modifyMaxTransactionFee.
					freezeWith(opclient)
					.sign(imagekey)
					.execute(opclient);

			
			TransactionReceipt receipt = txResponse.getReceipt(opclient);

			
			Status transactionStatus = receipt.status;

			System.out.println("The transaction consensus status is " + transactionStatus + ". You are a part of Platypi Kinetics!");
			System.out.println("Type 0 to display your NFT (encoded in Base64, use a decoder online to display images)");
            System.out.println("Type 1 for STEM");
            System.out.println("Type 2 for ICE");
            System.out.println("Type 3 for P2P Tutoring");
            int choice = clubinput.nextInt();
            
            if(choice == 0) {
            	FileContentsQuery query = new FileContentsQuery()
            		    .setFileId(peerfile);

            		//Sign with client operator private key and submit the query to a Hedera network
            		ByteString contents = query.execute(opclient);

            		//Change to Utf-8 encoding
            		String contentsToUtf8 = contents.toStringUtf8();

            		System.out.println(contentsToUtf8);
            }
            else if(choice == 1) {
            	chooseSTEM(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 2) {
            	chooseICE(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 3) {
            	chooseP2P(peerfile, opclient, imagekey, clubinput);
            }
	}
	
	private static void chooseP2P(FileId peerfile, Client opclient, PrivateKey imagekey, Scanner clubinput) throws Exception {
		//Adding P2P image to student's NFT file
	      File P2Pimage = new File("C:\\Users\\pastr\\OneDrive\\Desktop\\p2ptoken.jpg");
	        
	      String encodedP2Pimage = (" " + encodeFileToBase64(P2Pimage));
	      
	      FileAppendTransaction appendP2P = new FileAppendTransaction()
				    .setFileId(peerfile)
				    .setContents("\n" + encodedP2Pimage);
			FileAppendTransaction modifyMaxTransactionFee = appendP2P.setMaxTransactionFee(new Hbar(2)); 

			
			TransactionResponse txResponse = modifyMaxTransactionFee.
					freezeWith(opclient)
					.sign(imagekey)
					.execute(opclient);

			
			TransactionReceipt receipt = txResponse.getReceipt(opclient);

			
			Status transactionStatus = receipt.status;

			System.out.println("The transaction consensus status is " + transactionStatus + ". You are a part of P2P Tutoring!");
			System.out.println("Type 0 to display your NFT (encoded in Base64, use a decoder online to display images)");
            System.out.println("Type 1 for STEM");
            System.out.println("Type 2 for ICE");
            System.out.println("Type 3 for Robotics");
            int choice = clubinput.nextInt();
            
            if(choice == 0) {
            	FileContentsQuery query = new FileContentsQuery()
            		    .setFileId(peerfile);

            		//Sign with client operator private key and submit the query to a Hedera network
            		ByteString contents = query.execute(opclient);

            		//Change to Utf-8 encoding
            		String contentsToUtf8 = contents.toStringUtf8();

            		System.out.println(contentsToUtf8);
            }
            else if(choice == 1) {
            	chooseSTEM(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 2) {
            	chooseICE(peerfile, opclient, imagekey, clubinput);
            }
            else if(choice == 3) {
            	chooseRobot(peerfile, opclient, imagekey, clubinput);
            }
	}
	
	private static void chooseClubs(int i, AccountId peeracc, PrivateKey peerkey, FileId peerfile, PrivateKey imagekey, Client opclient) throws Exception {
		Scanner clubinput = new Scanner(System.in);
	       
		if(i == 0) {
			chooseSTEM(peerfile, opclient, imagekey, clubinput);
		}
		else if(i == 1) {
			chooseICE(peerfile, opclient, imagekey, clubinput);
		}
		else if(i == 2) {
			chooseRobot(peerfile, opclient, imagekey, clubinput);
			   
		}
		else if(i == 3) {
			chooseP2P(peerfile, opclient, imagekey, clubinput);
		}
		
	}

	public static void main(String[] args) throws Exception {
		
		
		AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));
        
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        
        Scanner tokinput = new Scanner(System.in);
        System.out.println("Create new Student NFT account? Y/N");
        String create = tokinput.nextLine();
        System.out.println("What is your full name?");
        String studentname = tokinput.nextLine();
        
        if(create.equals("Y") || create.equals("y")) {
        	PrivateKey studentAccountPrivateKey = PrivateKey.generate();
            PublicKey studentAccountPublicKey = studentAccountPrivateKey.getPublicKey();
            
            TransactionResponse studentAccount = new AccountCreateTransaction()
            	     .setKey(studentAccountPublicKey)
            	     .setInitialBalance( Hbar.fromTinybars(1000)) //Student account starts with 1000 tinybars
            	     .execute(client);
            
            AccountId studentAccountId = studentAccount.getReceipt(client).accountId;
            System.out.println("Your student account ID is: " + studentAccountId);
            System.out.println("To create your student NFT, specify the file location of your image. Image must be smaller than 4kb."); //Right now, Hedera files can only be up to 4kb, IPFS can handle bigger files, might switch later
            String location = tokinput.nextLine();
            
            File nftlocal =  new File(location);
            String encodedimage = encodeFileToBase64(nftlocal);
            
         
            
            PrivateKey NFTImagePrivateKey = PrivateKey.generate();
            FileCreateTransaction nftblockchain = new FileCreateTransaction()
            	    .setKeys(NFTImagePrivateKey) 
            	    .setContents(encodedimage);
            

            		
            FileCreateTransaction modifyMaxTransactionFee = nftblockchain.setMaxTransactionFee(new Hbar(2)); 
        	TransactionResponse txResponse = modifyMaxTransactionFee
        			.freezeWith(client)
        			.sign(NFTImagePrivateKey)
        			.execute(client);
        	
        	TransactionReceipt receipt = txResponse.getReceipt(client);

        	FileId NFTFileId = receipt.fileId;

        	System.out.println("The address of your NFT image on the hashgraph is: " + NFTFileId);
        	
        	
        	PrivateKey adminKey = PrivateKey.generate();
            PrivateKey kyckey = PrivateKey.generate();
        	
            TokenCreateTransaction studenttoken = new TokenCreateTransaction() //Creating token and preparing for Hedera testnet transaction
        	        .setTokenName(studentname + "'s NFT")
        	        .setTokenSymbol(NFTFileId.toString()) //Setting the token symbol as the address of the file on the hashgraph so that the token associates with the image
        	        .setTokenMemo("HEDERA://" + NFTFileId.toString()) //Setting token memo as file ID so that it is easier to find NFT on search engines like DragonGlass
        	        .setTreasuryAccountId(studentAccountId) //Giving the token to the student's account
        	        .setInitialSupply(1) //Supply of token is 1, so that the token is non-fungible
        	        .setAdminKey(adminKey.getPublicKey()) //Signing with client's admin key so that the transaction is verified with the NFT client
        	        .setKycKey(kyckey) //KYC key is so that student can easily check whether token belongs to them or not
        	        .setMaxTransactionFee(new Hbar(30)); //Maximum fee for transferring token to another account
            
            TransactionResponse txResponsetoken = studenttoken //Completing token creation using transaction, signing with student's private key
           		 .freezeWith(client)
           		 .sign(adminKey)
           		 .sign(studentAccountPrivateKey)
           		 .execute(client);

            
            TransactionReceipt receipttoken = txResponsetoken.getReceipt(client); //Getting the receipt of the transaction to display token's ID

            
            TokenId studenttokenId = receipttoken.tokenId; //Getting token's ID from the receipt

            System.out.println("The ID of your student token is " + studenttokenId);
            
            TokenInfoQuery namequery = new TokenInfoQuery() //Searching for NFT name on the hashgraph
                    .setTokenId(studenttokenId);

               
            String nftname = namequery.execute(client).name; //Completing search transaction
                
            TokenInfoQuery symbolquery = new TokenInfoQuery() //Searching for NFT symbol on the hashgraph, symbol contains image address
               		 .setTokenId(studenttokenId);

                    
            String nftsymbol = symbolquery.execute(client).symbol; //Completing search transaction 

            System.out.println("The token name is " + nftname);
            System.out.println("The token symbol is " + nftsymbol);
            System.out.println("What clubs are you a part of?");
            System.out.println("Type 0 for STEM Club");
            System.out.println("Type 1 for ICE");
            System.out.println("Type 2 for Robotics Club");
            System.out.println("Type 3 for P2P Tutoring");
            int clubchoice = tokinput.nextInt();
            chooseClubs(clubchoice, studentAccountId, studentAccountPrivateKey, NFTFileId, NFTImagePrivateKey, client);
        }
        
	}

}
