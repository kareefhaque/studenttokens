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
	
	
	private static void chooseClubs(int i, AccountId peeracc, PrivateKey peerkey, Client opclient) throws Exception {
		
		
		//Create STEM Token
		PrivateKey STEMAccountPrivateKey = PrivateKey.generate();
        PublicKey STEMAccountPublicKey = STEMAccountPrivateKey.getPublicKey();
        
        TransactionResponse STEMAccount = new AccountCreateTransaction()
        	     .setKey(STEMAccountPublicKey)
        	     .setInitialBalance( Hbar.fromTinybars(1000)) //Student account starts with 1000 tinybars
        	     .execute(opclient);
        
        AccountId STEMAccountId = STEMAccount.getReceipt(opclient).accountId;
        
        PrivateKey STEMadminKey = PrivateKey.generate();
        
        PrivateKey STEMkycKey = PrivateKey.generate();
        
        File STEMimage = new File("C:\\Users\\pastr\\OneDrive\\Desktop\\stemtoken.jpg");
        
        String encodedSTEMimage = encodeFileToBase64(STEMimage);
        
        PrivateKey STEMImagePrivateKey = PrivateKey.generate();
        FileCreateTransaction STEMblockchain = new FileCreateTransaction()
        	    .setKeys(STEMImagePrivateKey) 
        	    .setContents(encodedSTEMimage);
        

        FileCreateTransaction STEMmodifyMaxTransactionFee = STEMblockchain.setMaxTransactionFee(new Hbar(2)); 
    	TransactionResponse STEMtxResponse = STEMmodifyMaxTransactionFee
    			.freezeWith(opclient)
    			.sign(STEMImagePrivateKey)
    			.execute(opclient);
    	
    	TransactionReceipt STEMreceipt = STEMtxResponse.getReceipt(opclient);

    	FileId STEMFileId = STEMreceipt.fileId;
        
        TokenCreateTransaction STEMtoken = new TokenCreateTransaction() //Creating token and preparing for Hedera testnet transaction
    	        .setTokenName("STEM Team Activity Token")
    	        .setTokenSymbol(STEMFileId.toString()) //Setting the token symbol as the address of the file on the hashgraph so that the token associates with the image
    	        .setTokenMemo(STEMFileId.toString()) //Setting token memo as file ID so that it is easier to find NFT on search engines like DragonGlass
    	        .setTreasuryAccountId(STEMAccountId) //Giving the token to the student's account
    	        .setInitialSupply(200) //Supply of token is 1, so that the token is non-fungible
    	        .setAdminKey(STEMadminKey.getPublicKey()) //Signing with client's admin key so that the transaction is verified with the NFT client
    	        .setKycKey(STEMkycKey) //KYC key is so that student can easily check whether token belongs to them or not
    	        .setMaxTransactionFee(new Hbar(30)); //Maximum fee for transferring token to another account
        
        TransactionResponse STEMtxResponsetoken = STEMtoken //Completing token creation using transaction, signing with supervisor's private key
          		 .freezeWith(opclient)
          		 .sign(STEMadminKey)
          		 .sign(STEMAccountPrivateKey)
          		 .execute(opclient);
       
        TransactionReceipt STEMreceipttoken = STEMtxResponsetoken.getReceipt(opclient); //Getting the receipt of the transaction to display token's ID

           
        TokenId STEMtokenId = STEMreceipttoken.tokenId; //Getting token's ID from the receipt
        
        //Create ICE Token
        PrivateKey ICEAccountPrivateKey = PrivateKey.generate();
        PublicKey ICEAccountPublicKey = ICEAccountPrivateKey.getPublicKey();
        
        TransactionResponse ICEAccount = new AccountCreateTransaction()
        	     .setKey(ICEAccountPublicKey)
        	     .setInitialBalance( Hbar.fromTinybars(1000)) //Student account starts with 1000 tinybars
        	     .execute(opclient);
        
        AccountId ICEAccountId = ICEAccount.getReceipt(opclient).accountId;
        
        PrivateKey ICEadminKey = PrivateKey.generate();
        
        PrivateKey ICEkycKey = PrivateKey.generate();
        
        File ICEimage = new File("C:\\Users\\pastr\\OneDrive\\Desktop\\icetoken.jpg");
        
        String encodedICEimage = encodeFileToBase64(ICEimage);
        
        PrivateKey ICEImagePrivateKey = PrivateKey.generate();
        FileCreateTransaction ICEblockchain = new FileCreateTransaction()
        	    .setKeys(ICEImagePrivateKey) 
        	    .setContents(encodedICEimage);
        

        FileCreateTransaction ICEmodifyMaxTransactionFee = ICEblockchain.setMaxTransactionFee(new Hbar(2)); 
    	TransactionResponse ICEtxResponse = ICEmodifyMaxTransactionFee
    			.freezeWith(opclient)
    			.sign(ICEImagePrivateKey)
    			.execute(opclient);
    	
    	TransactionReceipt ICEreceipt = ICEtxResponse.getReceipt(opclient);

    	FileId ICEFileId = ICEreceipt.fileId;
        
        TokenCreateTransaction ICEtoken = new TokenCreateTransaction() //Creating token and preparing for Hedera testnet transaction
    	        .setTokenName("ICE Team Activity Token")
    	        .setTokenSymbol(ICEFileId.toString()) //Setting the token symbol as the address of the file on the hashgraph so that the token associates with the image
    	        .setTokenMemo(ICEFileId.toString()) //Setting token memo as file ID so that it is easier to find NFT on search engines like DragonGlass
    	        .setTreasuryAccountId(ICEAccountId) //Giving the token to the student's account
    	        .setInitialSupply(200) //Supply of token is 1, so that the token is non-fungible
    	        .setAdminKey(ICEadminKey.getPublicKey()) //Signing with client's admin key so that the transaction is verified with the NFT client
    	        .setKycKey(ICEkycKey) //KYC key is so that student can easily check whether token belongs to them or not
    	        .setMaxTransactionFee(new Hbar(30)); //Maximum fee for transferring token to another account
        
        TransactionResponse ICEtxResponsetoken = ICEtoken //Completing token creation using transaction, signing with supervisor's private key
         		 .freezeWith(opclient)
         		 .sign(ICEadminKey)
         		 .sign(ICEAccountPrivateKey)
         		 .execute(opclient);
      
       TransactionReceipt ICEreceipttoken = ICEtxResponsetoken.getReceipt(opclient); //Getting the receipt of the transaction to display token's ID

          
       TokenId ICEtokenId = ICEreceipttoken.tokenId; //Getting token's ID from the receipt
       
        
        
		if(i == 0) {
			TransferTransaction transaction = new TransferTransaction()
				     .addTokenTransfer(STEMtokenId, STEMAccountId, -1)
				     .addTokenTransfer(STEMtokenId, peeracc, 1);

				//Sign with the client operator key and submit the transaction to a Hedera network
			TransactionResponse STEMtransferResponse = transaction.execute(opclient);

				//Request the receipt of the transaction
			TransactionReceipt STEMtransferreceipt = STEMtransferResponse.getReceipt(opclient);

				//Get the transaction consensus status
			Status STEMtransfertransactionStatus = STEMtransferreceipt.status;

			System.out.println("The transaction consensus status is " + STEMtransfertransactionStatus);

		}
		else if(i == 1) {
			//transferICEToken();
		}
		else if(i == 2) {
			//transferRobotToken();
		}
		else if(i == 3) {
			//tranferP2PToken();
		}
		else {
			System.out.println("You did not pick a club. Displaying token balances...");
            AccountBalanceQuery tokenquery = new AccountBalanceQuery()
       			 .setAccountId(peeracc);

       			//Sign with the operator private key and submit to a Hedera network
       			AccountBalance tokenBalance = tokenquery.execute(opclient);

       			System.out.println("The token balance(s) for this account: " +tokenBalance.token);
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
            System.out.println("To create your student NFT, specify the file location of your image. Image must be smaller than 4kb.");
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
        	        .setTokenMemo("Image Address: " + NFTFileId.toString()) //Setting token memo as file ID so that it is easier to find NFT on search engines like DragonGlass
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
            chooseClubs(clubchoice, studentAccountId, studentAccountPrivateKey, client);
        }
        
	}

}
