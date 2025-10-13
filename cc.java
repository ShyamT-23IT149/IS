import java.util.*;

public class cc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String alp = "abcdefghijklmnopqrstuvwxyz";

        System.out.print("Enter plaintext (will be lowered, non-letters preserved): ");
        String word = sc.nextLine();

        System.out.print("Enter key (integer): ");
        if (!sc.hasNextInt()) {
            System.out.println("Key must be an integer.");
            sc.close();
            return;
        }
        int key = sc.nextInt();
        key = ((key % 26) + 26) % 26; // normalize to 0..25

        // ENCRYPT
        StringBuilder cipherBuilder = new StringBuilder();
        for (char ch : word.toCharArray()) {
            if (Character.isLetter(ch)) {
                char lower = Character.toLowerCase(ch);
                int idx = alp.indexOf(lower);
                char enc = alp.charAt((idx + key) % 26);
                cipherBuilder.append(enc);
            } else {
                cipherBuilder.append(ch); // keep spaces/punct
            }
        }
        String cipher = cipherBuilder.toString();
        System.out.println("\nCiphertext:");
        System.out.println(cipher);

        // DECRYPT (using same key)
        StringBuilder plainBuilder = new StringBuilder();
        for (char ch : cipher.toCharArray()) {
            if (Character.isLetter(ch)) {
                int idx = alp.indexOf(ch);
                int d = (idx - key + 26) % 26;
                plainBuilder.append(alp.charAt(d));
            } else {
                plainBuilder.append(ch);
            }
        }
        System.out.println("\nDecrypted with given key:");
        System.out.println(plainBuilder.toString());

        // BRUTE FORCE
        System.out.println("\nBRUTE FORCE APPROACH !!!");
        for (int i = 0; i < 26; i++) {
            StringBuilder br = new StringBuilder();
            for (char ch : cipher.toCharArray()) {
                if (Character.isLetter(ch)) {
                    int id = alp.indexOf(ch);
                    id = (id - i + 26) % 26;
                    br.append(alp.charAt(id));
                } else {
                    br.append(ch);
                }
            }
            System.out.println("key=" + i + " -> " + br.toString());
        }

        // FREQUENCY ATTACK
        System.out.println("\nFREQUENCY ATTACK !!!");
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : cipher.toCharArray()) {
            if (Character.isLetter(ch)) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }

        if (map.isEmpty()) {
            System.out.println("No letters in ciphertext to analyse.");
            sc.close();
            return;
        }

        // Most frequent ciphertext letter
        char maxi = '\0';
        int maxii = -1;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxii) {
                maxii = entry.getValue();
                maxi = entry.getKey();
            }
        }
        System.out.println("Most frequent ciphertext character: " + maxi + " (count=" + maxii + ")");

        char[] mostfreq = {'e', 't', 'a', 'o', 'i'}; // guesses (common English letters)
        sc.nextLine(); // consume newline before interactive prompts (if needed)

        for (char guess : mostfreq) {
            int k = (alp.indexOf(maxi) - alp.indexOf(guess) + 26) % 26;
            System.out.println("\nTrying assumption ciphertext '" + maxi + "' -> plaintext '" + guess + "' => guessed key = " + k);

            StringBuilder pp = new StringBuilder();
            for (char chh : cipher.toCharArray()) {
                if (Character.isLetter(chh)) {
                    int idxx = alp.indexOf(chh);
                    idxx = (idxx - k + 26) % 26;
                    pp.append(alp.charAt(idxx));
                } else {
                    pp.append(chh);
                }
            }

            System.out.println("Result: " + pp.toString());
            System.out.print("Is this correct plaintext? (yes/no): ");
            String nn = sc.nextLine().trim().toLowerCase();
            if (nn.equals("yes")) {
                System.out.println("Recovered plaintext (by frequency guess): " + pp.toString());
                break;
            }
        }

        sc.close();
    }
}
