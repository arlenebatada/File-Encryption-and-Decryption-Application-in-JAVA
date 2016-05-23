/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import static javax.swing.JFileChooser.FILES_AND_DIRECTORIES;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Arlene
 */
public class FileChooser extends javax.swing.JFrame
{

    /**
     * Creates new form FileChooser
     */
    private File[] listOfFilesAndFolders;
    private String encryptOrDecrypt;
    boolean addingNewFilesToExisting;
    
    public FileChooser(String encryptOrDecrypt)
    {
        this.encryptOrDecrypt=encryptOrDecrypt;
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        jFileChooser1.setDialogTitle("Select files & those folders whose files you wish to "+encryptOrDecrypt);
        jFileChooser1.setMultiSelectionEnabled(true);
        jFileChooser1.setFileSelectionMode(FILES_AND_DIRECTORIES);
        jFileChooser1.setDialogTitle(encryptOrDecrypt);
        if(encryptOrDecrypt.equalsIgnoreCase("encrypt"))
        {
            UIManager.put("FileChooser.openDialogTitleText", "Select Files To Encrypt");
            UIManager.put("FileChooser.openButtonText", "Select Files To Encrypt");
        }
        else if(encryptOrDecrypt.equalsIgnoreCase("decrypt"))
        {
            UIManager.put("FileChooser.openDialogTitleText", "Select Files To Decrypt");
            UIManager.put("FileChooser.openButtonText", "Select Files To Decrypt");
        }
        
        UIManager.put("FileChooser.cancelButtonText", "Cancel");
        UIManager.put("FileChooser.fileNameLabelText", "FileName");
        UIManager.put("FileChooser.filesOfTypeLabelText", "TypeFiles");
        UIManager.put("FileChooser.openButtonToolTipText", "Select File");
        UIManager.put("FileChooser.cancelButtonToolTipText","Cancel");
        UIManager.put("FileChooser.fileNameHeaderText","FileName");
        UIManager.put("FileChooser.upFolderToolTipText", "UpOneLevel");
        UIManager.put("FileChooser.homeFolderToolTipText","Desktop");
        UIManager.put("FileChooser.newFolderToolTipText","Create a NewFolder");
        UIManager.put("FileChooser.listViewButtonToolTipText","List");
        UIManager.put("FileChooser.filterLabelText", "TypeFiles");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Details");
        UIManager.put("FileChooser.fileSizeHeaderText","Size");
        UIManager.put("FileChooser.fileDateHeaderText", "DateModified");
        SwingUtilities.updateComponentTreeUI(jFileChooser1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {   
                setVisible(false);
                dispose();
                new Main(false).setVisible(true);
            }
        });
        
    }

    public FileChooser(String encryptOrDecrypt, File[] listOfFilesAndFolders)
    {
        this.encryptOrDecrypt=encryptOrDecrypt;
        this.listOfFilesAndFolders=listOfFilesAndFolders;
        addingNewFilesToExisting=true;
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        jFileChooser1.setDialogTitle("Select files & those folders whose files you wish to "+encryptOrDecrypt);
        jFileChooser1.setMultiSelectionEnabled(true);
        jFileChooser1.setFileSelectionMode(FILES_AND_DIRECTORIES);
        jFileChooser1.setDialogTitle(encryptOrDecrypt);
        if(encryptOrDecrypt.equalsIgnoreCase("encrypt"))
        {
            UIManager.put("FileChooser.openDialogTitleText", "Select Files To Encrypt");
            UIManager.put("FileChooser.openButtonText", "Select Files To Encrypt");
        }
        else if(encryptOrDecrypt.equalsIgnoreCase("decrypt"))
        {
            UIManager.put("FileChooser.openDialogTitleText", "Select Files To Decrypt");
            UIManager.put("FileChooser.openButtonText", "Select Files To Decrypt");
        }
        UIManager.put("FileChooser.cancelButtonText", "Cancel");
        UIManager.put("FileChooser.fileNameLabelText", "FileName");
        UIManager.put("FileChooser.filesOfTypeLabelText", "TypeFiles");
        UIManager.put("FileChooser.openButtonToolTipText", "Select File");
        UIManager.put("FileChooser.cancelButtonToolTipText","Cancel");
        UIManager.put("FileChooser.fileNameHeaderText","FileName");
        UIManager.put("FileChooser.upFolderToolTipText", "UpOneLevel");
        UIManager.put("FileChooser.homeFolderToolTipText","Desktop");
        UIManager.put("FileChooser.newFolderToolTipText","Create a NewFolder");
        UIManager.put("FileChooser.listViewButtonToolTipText","List");
        UIManager.put("FileChooser.filterLabelText", "TypeFiles");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Details");
        UIManager.put("FileChooser.fileSizeHeaderText","Size");
        UIManager.put("FileChooser.fileDateHeaderText", "DateModified");
        SwingUtilities.updateComponentTreeUI(jFileChooser1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {                
                setVisible(false);
                dispose();
                new Main(false).setVisible(true);
            }
        });
    }
    
    
    private boolean anyEncFileExists(File[] listOfFilesAndFolders)
    {
        for(File file:listOfFilesAndFolders)
        {
            String filePath=file.getAbsoluteFile().toString();
            if(!file.isDirectory() && filePath.substring(filePath.length()-4, filePath.length()).equals(".enc"))
            {
                System.out.println(file.getAbsoluteFile()+ " is already encrypted");
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "1 or more files are already encrypted !! \nPlease de-select them.");
                return true;
            } 
        }
        return false;
    }
    
    private boolean allAreEncFiles(File[] listOfFilesAndFolders)
    {
       for(File file:listOfFilesAndFolders)
        {
            String filePath=file.getAbsoluteFile().toString();
            if(!file.isDirectory() &&!filePath.substring(filePath.length()-4, filePath.length()).equals(".enc"))
            {
                System.out.println(file.getAbsoluteFile()+ " is not encrypted");
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "1 or more files are not encrypted !! \nPlease de-select them.");
                return false;
            } 
        }
        return true;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jFileChooser1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jFileChooser1ActionPerformed
    {//GEN-HEADEREND:event_jFileChooser1ActionPerformed
        
        if(evt.getActionCommand().toString().equals("CancelSelection"))
        {
            if(addingNewFilesToExisting)
            {
               new ToBeEncryptedOrDecrypted(this.listOfFilesAndFolders, encryptOrDecrypt).setVisible(true);
               setVisible(false);
               dispose();
            }
                    
            else if(!addingNewFilesToExisting)
            {
                new Main(false).setVisible(true);
                setVisible(false);
                dispose();
            }
            
        }
        
        else if(evt.getActionCommand().toString().equals("ApproveSelection"))
        {
            
            if(!addingNewFilesToExisting)
            {
                this.listOfFilesAndFolders=jFileChooser1.getSelectedFiles();
            }
            
            else if(addingNewFilesToExisting)
            {
                File[] newFilesAndFolders=jFileChooser1.getSelectedFiles();
             
                
                int newLength= newFilesAndFolders.length+this.listOfFilesAndFolders.length;
        
                File[] combinedFileArray = new File[newLength];
        
                int i;
                
                for(i=0; i<this.listOfFilesAndFolders.length; i++)
                {
                    combinedFileArray[i]=this.listOfFilesAndFolders[i];
                }
                for(int j=0; j<newFilesAndFolders.length; j++)
                {
                    combinedFileArray[i++]=newFilesAndFolders[j];
                }
                
                this.listOfFilesAndFolders = combinedFileArray;
            }
           
            
            
            
           if(encryptOrDecrypt.equalsIgnoreCase("encrypt") && !anyEncFileExists(listOfFilesAndFolders))
           {
               new ToBeEncryptedOrDecrypted(this.listOfFilesAndFolders, encryptOrDecrypt).setVisible(true);
               setVisible(false);
               dispose();
           }
           
           else if(encryptOrDecrypt.equalsIgnoreCase("decrypt") && allAreEncFiles(listOfFilesAndFolders))
           {
                   new ToBeEncryptedOrDecrypted(listOfFilesAndFolders, encryptOrDecrypt).setVisible(true);
                   setVisible(false);
                   dispose();
           }
           
        }        
        
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
 
}