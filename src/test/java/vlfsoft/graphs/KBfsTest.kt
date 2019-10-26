package vlfsoft.graphs

import org.junit.jupiter.api.Test

/**
 * https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-3/src/main/java/com/baeldung/algorithms/breadthfirstsearch
 * 6.1 BinaryTree: A Basic Binary Tree http://opendatastructures.org/versions/edition-0.1e/ods-java/6_1_BinaryTree_Basic_Binary.html#sec:bintree:traversal
 */
class KBfsTest {

    enum class PrintSearchTitle {
        NotOrdered,
        LeftToRightOrdered,
        RightToLeftOrdered
    }

    private inline fun printSearch(title: PrintSearchTitle, block: () -> Unit) {
        println("--${title.name}--")
        block()
        println()
    }

    /**
     * Breadth-First Search Algorithm in Java https://www.baeldung.com/java-breadth-first-search
     * The basic approach of the Breadth-First Search (BFS) algorithm is to search for a node into a tree or graph structure
     * by exploring neighbors before children.
     */
    @Test
    fun find_usingBFS_tree_baeldung() {

        val initBlock: Tree<Int>.() -> Unit = {
            addChild(2) {
                addChild(3)
            }
            addChild(4)
        }

        println("Tree")
        println()

        printSearch(PrintSearchTitle.NotOrdered) {
            treeWithNotOrderedChildrenOf(10) { initBlock() }.find_usingBFS(4)
        }

        printSearch(PrintSearchTitle.LeftToRightOrdered) {
            treeWithLeftToRightOrderedChildrenOf(10) { initBlock() }.find_usingBFS(4)
        }

        printSearch(PrintSearchTitle.RightToLeftOrdered) {
            treeRightToLeftOrderedChildrenOf(10) { initBlock() }.find_usingBFS(4)
        }
    }

    @Test
    fun find_usingBFS_graph_baeldung() {

        fun populateGraph(neighborsMutableCollectionStrategy: NeighborsMutableCollectionStrategyA<Int, Graph<Int>>): Graph<Int> {

            val start = Graph(10, neighborsMutableCollectionStrategy)
            val firstNeighbor = Graph(2, neighborsMutableCollectionStrategy)
            start.connect(firstNeighbor)

            val firstNeighborNeighbor = Graph(3, neighborsMutableCollectionStrategy)
            firstNeighbor.connect(firstNeighborNeighbor)
            firstNeighborNeighbor.connect(start)

            val secondNeighbor = Graph(4, neighborsMutableCollectionStrategy)
            start.connect(secondNeighbor)

            return firstNeighborNeighbor

        }

        println("Graph")
        println()

        printSearch(PrintSearchTitle.NotOrdered) {
            populateGraph(NeighborsMutableCollectionStandardStrategy.NotOrdered()).find_usingBFS(4)
        }

        printSearch(PrintSearchTitle.LeftToRightOrdered) {
            populateGraph(NeighborsMutableCollectionStandardStrategy.LeftToRightOrdered()).find_usingBFS(4)
        }

        printSearch(PrintSearchTitle.RightToLeftOrdered) {
            populateGraph(NeighborsMutableCollectionStandardStrategy.RightToLeftOrdered()).find_usingBFS(4)
        }

    }

}


