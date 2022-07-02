import pandas as pd
import matplotlib.pyplot as plt

if __name__ == '__main__':
    df = pd.read_csv("../Results/output.txt")
    x = df['Count']
    aapst = df['AAPST']
    splay = df['Splay']
    bst = df['BST']

    plt.plot(x, aapst, linestyle='solid', color='black', label="AAPST")
    plt.plot(x, splay, linestyle='dotted', color='black', label="Splay")
    plt.plot(x, bst, linestyle='dashed', color='black', label="BST")
    plt.title('Max Number of Comparisons - p = 0.9')
    plt.xlabel("Total Number of Queries")
    plt.ylabel("Max Number of Comparisons Required by a Query")
    plt.ylim(bottom=0)
    plt.legend()
    plt.show()

    # df = pd.read_csv("../Results/average.txt")
    # x = df['Count']
    # aapst = df['AAPST']
    # splay = df['Splay']
    # bst = df['BST']
    #
    # plt.plot(x, aapst, linestyle='solid', color='black', label="AAPST")
    # plt.plot(x, splay, linestyle='dotted', color='black', label="Splay")
    # plt.plot(x, bst, linestyle='dashed', color='black', label="BST")
    # plt.title('Average Number of Comparisons - p = 0.9')
    # plt.xlabel("Total Number of Queries")
    # plt.ylabel("Average Number of Comparisons Required by a Query")
    # plt.ylim(bottom=0)
    # plt.legend()
    # plt.show()

