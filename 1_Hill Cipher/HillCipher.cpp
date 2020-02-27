#include <iostream>
#include <sstream>
#include <algorithm>

using namespace std;

const int N = 4;
int arr2[4][4];

void getCofactor(int A[N][N], int temp[N][N], int p, int q, int n) {
  int i = 0, j = 0;
  
  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
      if (row != p && col != q) {
        temp[i][j++] = A[row][col];
        if (j == n - 1) {
          j = 0;
          i++;
        }
      }
    }
  }
}

int determinant(int A[N][N], int n) {
  int D = 0;
  
  if (n == 1)
    return A[0][0];

  int temp[N][N];
  int sign = 1;

  for (int f = 0; f < n; f++) {
    getCofactor(A, temp, 0, f, n);
    D += sign * A[0][f] * determinant(temp, n - 1);
    sign = -sign;
  }
  return D;
}

void adjoint(int A[N][N], int adj[N][N]) {
  if (N == 1) {
    adj[0][0] = 1;
    return;
  }

  int sign = 1, temp[N][N];

  for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
      getCofactor(A, temp, i, j, N);
      sign = ((i + j) % 2 == 0) ? 1 : -1;
      adj[j][i] = (sign) * (determinant(temp, N - 1));
    }
  }
}

bool inverse(int A[N][N], float inverse[N][N]) {
  int det = determinant(A, N);
  
  if (det == 0) {
    cout << "Singular matrix, can't find its inverse";
    return false;
  }

  int adj[N][N];
  adjoint(A, adj);

  for (int i = 0; i < N; i++)
    for (int j = 0; j < N; j++)
      inverse[i][j] = adj[i][j] / float(det);

  return true;
}
string hillcypher(string text, string key, int n) {
  int arr[4][1];
  
  for (int i = 0; i < 4; i++) {
    arr[i][0] = text[i] - 'A';
  }
  
  int ans[4][1];

  for (int i = 0; i < n; i++) {
    ans[i][0] = 0;
  }
  for (int i = 0; i < n; ++i)
    for (int j = 0; j < 1; ++j)
      for (int k = 0; k < n; ++k) {
        ans[i][j] += arr2[i][k] * arr[k][j];
      }

  string s = "";
  for (int i = 0; i < n; i++) {
    ans[i][0] %= 26;
    s += ans[i][0] + 'A';
  }
  return s;
}
string decrypt_hillcypher(string text, string key, int n) {
  int arr[4][1];
  string anss = "";
  
  for (int i = 0; i < 4; i++) 
    arr[i][0] = text[i] - 'A';

  float inv[4][4];
  int ans[4][1];
  
  for (int i = 0; i < 4; i++)
    ans[i][0] = 0;
    
  if (inverse(arr2, inv)) {
    for (int i = 0; i < 4; ++i)
      for (int j = 0; j < 1; ++j)
        for (int k = 0; k < 4; ++k) {
          ans[i][j] += inv[i][k] * arr[k][j];
        }
    string s = "";
    for (int i = 0; i < n; i++) {
      if (ans[i][0] < 0) {
        while (ans[i][0] <= 0) {
          ans[i][0] += 26;
        }
      }
      ans[i][0] %= 26;
      s += ans[i][0] + 'A';
    }
    return s;
  }
  return "";
}

int main(int argc, char ** argv) {
  string text, key, text2;
  text = argv[1];
  int n = text.length();
  key = argv[2];

  int xx = (n % 4 != 0) ? (((n / 4) * 4) + 4) - n : 0;

  cout << "The entered text is :-\n";
  cout << text << "\n";
  string tesxt2 = "";
  
  for (int i = 0; i < n; i++) {
    text2 += text[i];
  }
  
  for (int i = 0; i < xx; i++) {
    text2 += 'Z';
  }
  
  cout << "Text after padding:-\n";
  cout << text2 << "\n";
  cout << "Key is\n";
  cout << key << "\n";
  cout << "The matrix is:-\n";

  for (int i = 0; i < 4; i++) {
    for (int j = 0; j < 4; j++) {
      arr2[i][j] = key[4 * i + j] - 'A';
      cout << arr2[i][j] << " ";
    }
    cout << "\n";
  }
  
  if (determinant(arr2, 4) == 0) {

    cout << "Matrix not invertible else not accepted\n";
    return 0;
  }
  
  cout << "Inverse matrix :-\n";
  float inv[4][4];
  bool bo = inverse(arr2, inv);
  
  for (int i = 0; i < 4; i++) {
    for (int j = 0; j < 4; j++) {
      int lll;
      if (inv[i][j] < 0) {
        lll = (int)(-1 * inv[i][j]);
        lll = lll % 26;
        cout << 26 - lll << " ";
      } else {
        lll = (int) inv[i][j];
        cout << lll % 26 << " ";
      }
    }
    cout << "\n";
  }
  
  string ans = "";
  int nl = text.length();
  int len;
  string re = "", anss = "";
  
  for (int i = 0; i < nl; i += 4) {
    string str = "";
    
    for (int g = i; g < i + min(4, nl - i); g++) {
      str += text[g];
    }
    
    len = str.length();
    
    if (str.length() < 4) {
      while (str.length() != 4) {
        str += 'Z';
      }
      string x = hillcypher(str, key, 4);
      re = decrypt_hillcypher(x, key, 4);
      for (int i = 0; i < 4; i++) {
        ans += x[i];
        anss += re[i];
      }
      
    } else if (len == 4) {
      re = hillcypher(str, key, 4);
      anss += decrypt_hillcypher(re, key, 4);
      ans += re;
    }
  }

  cout << "Encrypted code is:- \n";
  cout << ans;
  cout << " \n";
  cout << "Decrypted code is:-\n";

  cout << anss;

  return 0;
}
