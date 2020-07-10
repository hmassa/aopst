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
Currently, I have compared the search performance of a splay tree and an access optimized search tree on 7 different datasets. These datasets include a Benford distribution, a Zipf distribution, a uniorm distribution, a normal distribution, and a Pareto distribution. The final two datasets tested are the letters from a song's lyrics and every work in the "Star Wars: Episode IV - A New Hope" movie sript. Search performance is based on the number of comparisons required to find the search key in each tree.

## Future Tests
For future tests, I would like to test the performance of other tree functions, such as insertion and deletion. Also, since the number of comparisons counted for the search function does not include the restructuring that takes place after the node has been found, so it would be interesting to include this in future tests as well.
