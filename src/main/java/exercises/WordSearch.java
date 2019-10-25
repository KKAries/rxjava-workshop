package exercises;

public class WordSearch {

  public static void main(String[] args) {
    char[][] matrix = {
        {'F', 'A', 'C', 'I'},
        {'O', 'B', 'Q', 'P'},
        {'A', 'N', 'O', 'B'},
        {'M', 'A', 'S', 'S'}};

    System.out.println(wordSearch(matrix, "FAC"));
  }

  public static boolean wordSearch(char[][] matrix, String word) {
    for (char[] array : matrix) {
      if (search(array, word)) {
        return true;
      }
    }

    for (int i = 0; i < matrix[0].length; i++) {
      char[] array = new char[matrix.length];
      for (int j = 0; j < matrix.length; j++) {
        array[j] = matrix[j][i];
      }
      if (search(array, word)) {
        return true;
      }
    }
    return false;
  }

  private static boolean search(char[] charArray, String word) {
    int index = 0;
    while (index < charArray.length) {
      if (charArray[index] != word.charAt(index)) {
        return false;
      }
      index++;
      if (index == word.length()) {
        return true;
      }
    }
    return false;
  }

}
