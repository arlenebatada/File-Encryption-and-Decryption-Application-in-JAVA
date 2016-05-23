
package core;

import gui.ExceptionDialog;
import gui.SourceFileNotDeletedDuringDecryption;
import gui.SourceFileNotDeletedDuringEncryption;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;


/**
 *
 * @author Arlene
 */
public class FileEncryptorAndDecryptor
{
    private File destinationFile;
    private double accumulator=0;
    
    
    boolean areHashesEqual(File file, String keyHash) throws FileNotFoundException, IOException
    {
        
        BufferedInputStream fileReader=new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
        //reading key hash from file
        StringBuffer keyHashFromFile=new StringBuffer(128);
        for(int i=0; i<128; i++)
        {
            keyHashFromFile.append((char)fileReader.read());
        }
                
        //verifying both hashes
        System.out.println("keyHashFromFile.to string()= "+keyHashFromFile);
        System.out.println("keyHash= "+keyHash);
        fileReader.close();
        if(keyHashFromFile.toString().equals(keyHash))
        {
            return true;
        }
        return false;
    }
    
    private byte[] getHashInBytes(String key) throws NoSuchAlgorithmException
    {
        byte[] keyHash;
        final MessageDigest md = MessageDigest.getInstance("SHA-512");
                keyHash = md.digest(key.getBytes());
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< keyHash.length ;i++)
                {
                    sb.append(Integer.toString((keyHash[i] & 0xff) + 0x100, 16).substring(1));
                }
                String hashOfPassword = sb.toString();
                System.out.println("hashOfPassword length= "+hashOfPassword.length());
                System.out.println("hashOfPassword = " +hashOfPassword);
                return hashOfPassword.getBytes();
                
    }
    
    private String getHashInString(String key) throws NoSuchAlgorithmException
    {
        byte[] keyHash;
        final MessageDigest md = MessageDigest.getInstance("SHA-512");
                keyHash = md.digest(key.getBytes());
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< keyHash.length ;i++)
                {
                    sb.append(Integer.toString((keyHash[i] & 0xff) + 0x100, 16).substring(1));
                }
                String hashOfPassword = sb.toString();
                System.out.println("hashOfPassword length= "+hashOfPassword.length());
                System.out.println("hashOfPassword = " +hashOfPassword);
                return hashOfPassword;
                
    }
    
    
    public void encrypt(File file, String key, JProgressBar progressBar, JLabel progressPercentLabel, long totalSizeOfAllFiles, JTextArea progressOfFilesTextField)
    {
        byte[] keyHash;
        double percentageOfFileCopied=0;
        if(!file.isDirectory())
        {
            try
            {
                keyHash=getHashInBytes(key);
                
                destinationFile=new File(file.getAbsolutePath().concat(".enc"));
                if(destinationFile.exists())
                {
                    destinationFile.delete();
                    destinationFile=new File(file.getAbsolutePath().concat(".enc"));
                }
                
                BufferedInputStream fileReader=new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
                FileOutputStream fileWriter=new FileOutputStream (destinationFile, true);
                
                //writing key hash to file
                fileWriter.write(keyHash, 0, 128); 
                
                //encrypting content & writing
                byte[] buffer = new byte[262144];
                int bufferSize=buffer.length;
                int keySize=key.length();
                progressOfFilesTextField.setText(progressOfFilesTextField.getText()+"  0%\n");
                while(fileReader.available()>0)
                {
                    int bytesCopied=fileReader.read(buffer);
                    for(int i=0,keyCounter=0; i<bufferSize; i++, keyCounter%=keySize )
                    {

                        buffer[i]+=key.toCharArray()[keyCounter];
                    }
                    
                    fileWriter.write(buffer, 0, bytesCopied);
                    long fileLength=file.length();
                    percentageOfFileCopied+= (((double)bytesCopied/fileLength)*100);
                    showProgressOnprogressOfFilesTextField(progressOfFilesTextField, percentageOfFileCopied, bytesCopied, fileLength);
                    
                    showProgressOnProgressBarAndProgressPercent(progressBar, progressPercentLabel, bytesCopied, totalSizeOfAllFiles);
                    
                    System.out.println("des file length= "+destinationFile.length());
                }
                progressOfFilesTextField.setText(progressOfFilesTextField.getText().substring(0, progressOfFilesTextField.getText().length()-5)+"100%\n");
                fileReader.close();
                fileWriter.close();
                if(!file.delete())
                {
                    new SourceFileNotDeletedDuringEncryption(new javax.swing.JFrame(), true, file.getAbsolutePath()).setVisible(true);
                }
                
            } 
            catch (NoSuchAlgorithmException e)
            {
                new ExceptionDialog("NoSuchAlgorithmException!", "Something hugely badly unexpectadly went awfully wrong", e).setVisible(true);
                Logger.getLogger(FileEncryptorAndDecryptor.class.getName()).log(Level.SEVERE, null, e);
            }
            catch (SecurityException e)
            {
                new ExceptionDialog("File Security Error!!!", file+" doesn't allow you to do that!", e).setVisible(true);
            }
            catch (FileNotFoundException e)
            {
                new ExceptionDialog("File Not Found!!!", file+" not found!", e).setVisible(true);
            }
            catch (IOException e)
            {
                new ExceptionDialog("Can Not Read or Write file!!!", file+" can not be read or written!", e).setVisible(true);
            }
            catch (Exception e)
            {
                 new ExceptionDialog("Unexpected System Error!", "Something hugely badly unexpectadly went awfully wrong", e).setVisible(true);
            }
            
        }
    }
    
    public void decrypt(File file, String key, JProgressBar progressBar, JLabel progressPercent, long totalSizeOfAllFiles, JTextArea progressOfFilesTextField)
    {
        String keyHash;
        double percentageOfFileCopied=0;
        if(!file.isDirectory())
        {
            try
            {
                keyHash=getHashInString(key);
                
                
                if( areHashesEqual(file, keyHash))
                {
                    destinationFile=new File(file.getAbsolutePath().toString().substring(0, file.getAbsolutePath().toString().length()-4));
                
                    BufferedInputStream fileReader=new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
                    FileOutputStream fileWriter=new FileOutputStream (destinationFile);
                
                     //decrypting content & writing
                    byte[] buffer = new byte[262144];
                    int bufferSize=buffer.length;
                    int keySize=key.length();
                    progressOfFilesTextField.setText(progressOfFilesTextField.getText()+"  0%\n");
                    for(int i=0; i<128; i++)
                    {
                        if(fileReader.available()>0)
                        {
                            fileReader.read();
                        }
                    }
                    while(fileReader.available()>0)
                    {
                        int bytesCopied=fileReader.read(buffer);
                        for(int i=0,keyCounter=0; i<bufferSize; i++, keyCounter%=keySize )
                        {

                            buffer[i]-=key.toCharArray()[keyCounter];
                        }

                        fileWriter.write(buffer, 0, bytesCopied);
                        long fileLength=file.length();
                        percentageOfFileCopied+= (((double)bytesCopied/fileLength)*100);
                        showProgressOnprogressOfFilesTextField(progressOfFilesTextField, percentageOfFileCopied, bytesCopied, fileLength);

                        showProgressOnProgressBarAndProgressPercent(progressBar, progressPercent, bytesCopied, totalSizeOfAllFiles);
                        System.out.println("des file length= "+destinationFile.length());
                    
                    }
                    progressOfFilesTextField.setText(progressOfFilesTextField.getText().substring(0, progressOfFilesTextField.getText().length()-5)+"100%\n");
                    fileReader.close();
                    fileWriter.close();
                    if(!file.delete())
                    {
                        new SourceFileNotDeletedDuringDecryption(new javax.swing.JFrame(), true, file.getAbsolutePath()).setVisible(true);
                    }
                }
                else if(!areHashesEqual(file, keyHash))
                {
                    progressOfFilesTextField.append("\nPassword is verified using SHA-512 (128 bit) hash.\nLooks like the Input Password and the File Password differ!!\nEven if you bypass the hash (somehow) you won't be able to read the file because the file is encrypted at byte level.\nWithout the actual password you have no chance.\nYour Bad Luck ☺\n\n");
                }
                
            }
            catch (NoSuchAlgorithmException e)
            {
                new ExceptionDialog("NoSuchAlgorithmException!", "Something hugely badly unexpectadly went awfully wrong", e).setVisible(true);
                Logger.getLogger(FileEncryptorAndDecryptor.class.getName()).log(Level.SEVERE, null, e);
            }
            catch (SecurityException e)
            {
                new ExceptionDialog("File Security Error!!!", file+" doesn't allow you to do that!", e).setVisible(true);
            }
            catch (FileNotFoundException e)
            {
                new ExceptionDialog("File Not Found!!!", file+" not found!", e).setVisible(true);
            }
            catch (IOException e)
            {
                new ExceptionDialog("Can Not Read or Write file!!!", file+" can not be read or written!", e).setVisible(true);
            }            
            catch (Exception e)
            {
                 new ExceptionDialog("Unexpected System Error!", "Something hugely badly unexpectadly went awfully wrong", e).setVisible(true);
            }            
        }
    }
    
    private void showProgressOnProgressBarAndProgressPercent(JProgressBar progressBar, JLabel progressPercent, int bytesCopied, long totalSizeOfAllFiles)
    {
        int previousProgress=progressBar.getValue();
                    
                    accumulator+=((double)bytesCopied  /totalSizeOfAllFiles)*100 ;
                    
                    
                    if (accumulator >=1)
                    {
                            int percentage= previousProgress+ (int) accumulator;
                            accumulator=0;              
                            
                            progressBar.setValue(percentage);
                            progressPercent.setText(Integer.toString(percentage)+"%");
                    }
                    
    }
    
    private void showProgressOnprogressOfFilesTextField(JTextArea progressOfFilesTextField, double percentageOfFileCopied, int bytesCopied, long fileLength)
    {
        StringBuilder s=new StringBuilder(progressOfFilesTextField.getText().substring(0, progressOfFilesTextField.getText().length()-5));
        
        if(percentageOfFileCopied<10)
        {
            s.append("  "+Integer.toString((int)percentageOfFileCopied)+"%\n");
        }
        else if(percentageOfFileCopied <100)
        {
            s.append(" "+Integer.toString((int)percentageOfFileCopied)+"%\n");
        }
        else if(percentageOfFileCopied>=100)
        {
            s.append(Integer.toString((int)percentageOfFileCopied)+"%\n");                        
        }
                    
        progressOfFilesTextField.setText(s.toString());

    }
    
    

}
