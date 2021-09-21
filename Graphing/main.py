import pandas as pd
import matplotlib.pyplot as plt

if __name__ == '__main__':
    df = pd.read_csv("C:/Users/flipp/Documents/CompSci/Research/AOPST/output.txt")
    x = df['Count']

    plt.plot(x, df['AAPST'], linestyle='solid', color='black', label="AAPST")
    plt.plot(x, df['Splay'], linestyle='dotted', color='black', label="Splay")
    plt.plot(x, df['BST'], linestyle='dashed', color='black', label="BST")
    plt.legend()
    plt.show()

