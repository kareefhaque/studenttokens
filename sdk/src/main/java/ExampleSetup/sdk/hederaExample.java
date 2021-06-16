package ExampleSetup.sdk;

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
import com.hedera.hashgraph.sdk.HederaPreCheckStatusException;
import com.hedera.hashgraph.sdk.HederaReceiptStatusException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TokenCreateTransaction;
import com.hedera.hashgraph.sdk.TokenGrantKycTransaction;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenInfoQuery;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransferTransaction;

import io.github.cdimascio.dotenv.Dotenv;
import io.grpc.netty.shaded.io.netty.handler.timeout.TimeoutException;



public class hederaExample {
	
	public static void main(String[] args) throws TimeoutException, java.util.concurrent.TimeoutException, PrecheckStatusException, ReceiptStatusException {
		AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));
        
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        
        PrivateKey newAccountPrivateKey = PrivateKey.generate();
        PublicKey newAccountPublicKey = newAccountPrivateKey.getPublicKey();
        
        TransactionResponse newAccount = new AccountCreateTransaction()
        	     .setKey(newAccountPublicKey)
        	     .setInitialBalance( Hbar.fromTinybars(1000))
        	     .execute(client);
        
        AccountId newAccountId = newAccount.getReceipt(client).accountId;

        System.out.println("The new account ID is: " +newAccountId);
        
        AccountBalance accountBalance = new AccountBalanceQuery()
        	     .setAccountId(newAccountId)
        	     .execute(client);

        	System.out.println("The new account balance is: " +accountBalance.hbars);
        	
        	TransactionResponse sendHbar = new TransferTransaction()
                    .addHbarTransfer(myAccountId, Hbar.fromTinybars(-1000))
                    .addHbarTransfer(newAccountId, Hbar.fromTinybars(1000))
                    .execute(client);

            System.out.println("The transfer transaction was: " +sendHbar.getReceipt(client).status);
            
            Hbar queryCost = new AccountBalanceQuery()
                    .setAccountId(newAccountId)
                    .getCost(client);
            
            System.out.println("The cost of this query is: " +queryCost);
            		
        AccountBalance accountBalanceNew = new AccountBalanceQuery()
        			.setAccountId(newAccountId)
        			.execute(client);

            System.out.println("The new account balance is: " +accountBalanceNew.hbars);
            System.out.println("The old account balance is: " +accountBalance.hbars);
            PrivateKey newFilePrivateKey = PrivateKey.generate();
            FileCreateTransaction transaction = new FileCreateTransaction()
            	    .setKeys(newFilePrivateKey) 
            	    .setContents(Dotenv.load().get("BOOSIE_PIC"));
            	        
            	//Change the default max transaction fee to 2 hbars
            	FileCreateTransaction modifyMaxTransactionFee = transaction.setMaxTransactionFee(new Hbar(2)); 

            	//Prepare transaction for signing, sign with the key on the file, sign with the client operator key and submit to a Hedera network
            	TransactionResponse txResponse = modifyMaxTransactionFee
            			.freezeWith(client)
            			.sign(newFilePrivateKey)
            			.execute(client);

            	//Request the receipt
            	TransactionReceipt receipt = txResponse.getReceipt(client);

            	//Get the file ID
            	FileId newFileId = receipt.fileId;

            	System.out.println("The new file ID is: " + newFileId);
            	
            	FileContentsQuery query = new FileContentsQuery()
            		    .setFileId(newFileId);

            		//Sign with client operator private key and submit the query to a Hedera network
            		ByteString contents = query.execute(client);

            		//Change to Utf-8 encoding
            		String contentsToUtf8 = contents.toStringUtf8();

            		System.out.println(contentsToUtf8);
            		
               PrivateKey adminKey = PrivateKey.generate();
               PrivateKey kyckey = PrivateKey.generate();
               
               TokenCreateTransaction transactiontoken = new TokenCreateTransaction()
            	        .setTokenName("Lil Boosie")
            	        .setTokenSymbol(newFileId.toString())
            	        .setTreasuryAccountId(newAccountId)
            	        .setInitialSupply(1)
            	        .setAdminKey(adminKey.getPublicKey())
            	        .setKycKey(kyckey)
            	        .setMaxTransactionFee(new Hbar(30)); //Change the default max transaction fee
               
             TransactionResponse txResponsetoken = transactiontoken
            		 .freezeWith(client)
            		 .sign(adminKey)
            		 .sign(newAccountPrivateKey)
            		 .execute(client);

             //Request the receipt of the transaction
             TransactionReceipt receipttoken = txResponsetoken.getReceipt(client);

             //Get the token ID from the receipt
             TokenId tokenId = receipttoken.tokenId;

             System.out.println("The new token ID is " + tokenId);
             
           //Create the query
             TokenInfoQuery namequery = new TokenInfoQuery()
                 .setTokenId(tokenId);

             //Sign with the client operator private key, submit the query to the network and get the token supply
             String name = namequery.execute(client).name;
             
             TokenInfoQuery symbolquery = new TokenInfoQuery()
            		 .setTokenId(tokenId);

                 //Sign with the client operator private key, submit the query to the network and get the token supply
                 String symbol = symbolquery.execute(client).symbol;

             System.out.println("The token name is " +name);
             System.out.println("The token symbol is " +symbol);
             

            		
            AccountBalanceQuery tokenquery = new AccountBalanceQuery()
            			 .setAccountId(newAccountId);

            			//Sign with the operator private key and submit to a Hedera network
            			AccountBalance tokenBalance = tokenquery.execute(client);

            			System.out.println("The token balance(s) for this account: " +tokenBalance.token);
            			


             
             		
	}

}
