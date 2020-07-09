# Access Optimized Priority Search Tree
## Abstract
The priority search tree is a data structure introduced by Edward McCreight in 1985 with the primary purpose of storing a set of n points in ℝ² in a way that allows insertion, 
deletion, and various search algorithms to have an average and worst case time complexity of O(log n). This ideal complexity is achieved by combining the completeness of heaps 
with the efficient search algorithm of binary search trees. Because of these properties, and as we will show in this paper, priority search trees can be slightly altered to 
improve upon splay trees. The structural adjustments that result in this improvement include storing elements of the data set to be searched in the x-coordinate and the number 
of times the element has been accessed in the y-coordinate of a maximum priority search tree node. Like splay trees, this new data structure, which we will call an access 
optimized priority search tree, utilizes the principle of locality that is so often seen in computing. However, whereas splay trees have a worst case complexity of  O(n) for 
searching, access optimized priority search trees manage to reduce this worst case to O(log n) while preserving splay trees’ average time complexity.

## Current Tests
Currently, 3 different tests have been run comparing the performance of this new access optimized priority search tree to that of the commonly used splay tree. Two of the tests involve searching each character of a song's lyrics and a poem in trees constructed out of the English alphabet and recording and comparing the number of comaparisons required to find the correct node in each tree. The thrid test builds splay and AOPS trees out of the integers 1-9 and searches them for the leading digit of the populations of every country in the world, which approximately follows a Benford distribution. Again, the number of comparisons required for each algorithm to find the node with the correct integer is recorded.

## Future Tests
In progress is a test that examines the performance of these two algorithms on a dataset that adheres to Zipf's law. This test involves constructing trees out of the most commonly used words in the English language and using the words of a body of text as the search keys.
We are also planning to evaluate algorithm performance on parameterized datasets that vary from uniform to highly non-uniform.
