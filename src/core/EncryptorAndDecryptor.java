
package core;

import gui.EncryptorAndDecryptorProgress;
import gui.ExceptionDialog;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author Arlene
 */
public class EncryptorAndDecryptor extends SwingWorker <Boolean,Boolean>
{
    File[] listOfFilesAndFolders;
    String encryptOrDecrypt;
    String key;
    FileEncryptorAndDecryptor fileEncryptorAndDecryptor;
    int totalNumberOfFiles;
    long totalSizeOfAllFiles;
    boolean completedTask;
    JProgressBar progressBar;
    JTextArea progressOfFilesTextField;
    JLabel progressPercentLabel;
    JButton oKButton;
    EncryptorAndDecryptorProgress progressFrame;
    public EncryptorAndDecryptor(File[] listOfFilesAndFolders, String encryptOrDecrypt, String key)
    {
        this.listOfFilesAndFolders = listOfFilesAndFolders;
        this.encryptOrDecrypt = encryptOrDecrypt;
        this.key=key;
        progressFrame= new EncryptorAndDecryptorProgress(encryptOrDecrypt);
        progressFrame.setVisible(true);
        fileEncryptorAndDecryptor = new FileEncryptorAndDecryptor();
        progressBar = progressFrame.getProgressBar();
        progressOfFilesTextField = progressFrame.getProgressOfFilesTextField();
        progressPercentLabel = progressFrame.getProgressPercentLabel();
        oKButton=progressFrame.getoKButton();
        setTotalSizeAndNumberOfAllFiles();
    }
    
    @Override
    protected Boolean doInBackground() 
    {
        try
        {
            if(encryptOrDecrypt.equalsIgnoreCase("encrypt"))
            {
                encrypt();
            }
            else if(encryptOrDecrypt.equalsIgnoreCase("decrypt"))
            {
                decrypt();            
            }
        }
        catch (Exception e)
        {
              new ExceptionDialog("Unexpected System Error!", "Something hugely badly unexpectadly went awfully wrong", e).setVisible(true);         
        }
        finally
        {
            return true;
        }
    }
    protected void done()
    {
        try
        {
        
        progressFrame.setCompletedTask(true);
        Toolkit.getDefaultToolkit().beep();
        oKButton.setVisible(true);
        oKButton.setEnabled(true);
        oKButton.setText("OK");
                    
        }
        catch (Exception e)
        {
            new ExceptionDialog("Unexpected System Error!", "Something hugely badly unexpectadly went awfully wrong", e).setVisible(true);
        }
    }
    
    
    private void encrypt()
    {
        for(File file:listOfFilesAndFolders)
        {
            encrypt(file);
        }
        progressBar.setValue(progressBar.getMaximum());
        progressPercentLabel.setText("100%");
    }
    private void encrypt(File file)
    {
        if(!file.isDirectory() && file.exists())
        {
            progressOfFilesTextField.append("Encrypting "+file.getAbsolutePath()+"\n");
            fileEncryptorAndDecryptor.encrypt(file, key, progressBar, progressPercentLabel, totalSizeOfAllFiles, progressOfFilesTextField);
            progressOfFilesTextField.append("Done!\n\n");
        }
        else if(file.isDirectory() && file.exists())
        {
            File[] filesInTheDirectory=file.listFiles();
            progressOfFilesTextField.append("\n~~~~~~~~~~~~~~~~~~~~   Inside "+file.getAbsolutePath()+"   ~~~~~~~~~~~~~~~~~~~~\n");
            for(File eachFileInTheDirectory:filesInTheDirectory)
            {
                encrypt(eachFileInTheDirectory);
            }
            progressOfFilesTextField.append("~~~~~~~~~~~~~~~~~~~~   End of "+file.getAbsolutePath()+"   ~~~~~~~~~~~~~~~~~~~~\n\n");
        }
    }
    
    private void decrypt()
    {
        for(File file:listOfFilesAndFolders)
        {
                decrypt(file);
        }
        progressBar.setValue(progressBar.getMaximum());
        progressPercentLabel.setText("100%");
    }
    private void decrypt(File file)
    {
        if(!file.isDirectory() && file.exists() && file.getName().substring(file.getName().length()-4, file.getName().length()).equalsIgnoreCase(".enc"))
        {
            progressOfFilesTextField.append("Decrypting "+file.getAbsolutePath()+"\n");
            fileEncryptorAndDecryptor.decrypt(file, key, progressBar, progressPercentLabel, totalSizeOfAllFiles, progressOfFilesTextField);
            progressOfFilesTextField.append("Done!\n\n");
        }
        else if(file.isDirectory() && file.exists())
        {
            File[] filesInTheDirectory=file.listFiles();
            progressOfFilesTextField.append("\n~~~~~~~~~~~~~~~~~~~~   Inside "+file.getAbsolutePath()+"   ~~~~~~~~~~~~~~~~~~~~\n");
            for(File eachFileInTheDirectory:filesInTheDirectory)
            {
                
                decrypt(eachFileInTheDirectory);
            }
            progressOfFilesTextField.append("~~~~~~~~~~~~~~~~~~~~   End of "+file.getAbsolutePath()+"   ~~~~~~~~~~~~~~~~~~~~\n\n");
        }
    }
    
    
    private void setTotalSizeAndNumberOfAllFiles()
    {
        for(File file:listOfFilesAndFolders)
        {
            setTotalSizeAndNumberOfAllFiles(file);
        }
    }
    
    private void setTotalSizeAndNumberOfAllFiles(File file)
    {
        if(!file.isDirectory() && file.exists())
        {
            totalNumberOfFiles++;
            totalSizeOfAllFiles+=file.length();
        }
        else if(file.isDirectory() && file.exists())
        {
            File[] filesInTheDirectory=file.listFiles();
            
            for(File eachFileInTheDirectory:filesInTheDirectory)
            {
                setTotalSizeAndNumberOfAllFiles(eachFileInTheDirectory);
            }
        }
    }
    
     
    
    
}