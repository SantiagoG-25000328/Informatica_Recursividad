import java.io.File;
import java.io.FilenameFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Autograder {
    public static void main(String[] args) {
        File treesDir = new File("tests/binary_trees/");
        File[] treeFiles = treesDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".binary_tree");
            }
        });

        if (treeFiles == null || treeFiles.length == 0) {
            System.out.println("No tests found.");
            return;
        }

        int totalTests = treeFiles.length;
        double scorePerTest = 100.0 / totalTests;
        double finalScore = 0;
        ArrayList<String> passedTests = new ArrayList<String>();
        ArrayList<String> failedTests = new ArrayList<String>();

        // Ensure output directory exists
        new File("tests/outputs").mkdirs();

        for (int i = 0; i < treeFiles.length; i++) {
            File treeFile = treeFiles[i];
            String fileName = treeFile.getName();
            String testName = fileName.substring(0, fileName.lastIndexOf(".binary_tree"));
            
            String stringsPath = "tests/strings/" + testName + ".txt";
            String outputPath = "tests/outputs/" + testName + ".txt";
            String expectedPath = "tests/expected/" + testName + ".txt";

            try {
                // Run the Main class for this test
                Main.main(new String[]{treeFile.getPath(), stringsPath, outputPath});

                // Compare output with expected
                if (compareFiles(outputPath, expectedPath)) {
                    finalScore += scorePerTest;
                    passedTests.add(testName);
                } else {
                    failedTests.add(testName);
                }
            } catch (Exception e) {
                System.out.println("Error running test " + testName + ": " + e.getMessage());
                failedTests.add(testName);
            }
        }

        System.out.println("Results:");
        for (int i = 0; i < passedTests.size(); i++) {
            System.out.println((String)passedTests.get(i) + ": PASSED");
        }
        for (int i = 0; i < failedTests.size(); i++) {
            System.out.println((String)failedTests.get(i) + ": FAILED");
        }
        System.out.println("Final Score: " + (Math.round(finalScore * 100.0) / 100.0));
    }

    private static boolean compareFiles(String path1, String path2) {
        BufferedReader r1 = null;
        BufferedReader r2 = null;
        try {
            r1 = new BufferedReader(new FileReader(path1));
            r2 = new BufferedReader(new FileReader(path2));
            String line1, line2;
            while (true) {
                line1 = r1.readLine();
                line2 = r2.readLine();
                if (line1 == null && line2 == null) return true;
                if (line1 == null || line2 == null) return false;
                if (!line1.trim().equals(line2.trim())) return false;
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (r1 != null) r1.close();
                if (r2 != null) r2.close();
            } catch (IOException e) {
                // Ignore close errors
            }
        }
    }
}
