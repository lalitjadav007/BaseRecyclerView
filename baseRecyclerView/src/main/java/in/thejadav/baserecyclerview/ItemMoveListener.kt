package `in`.thejadav.baserecyclerview

interface ItemMoveListener{
    fun moveItem(oldPosition: Int, newPosition: Int)
//    fun itemSwiped(position: Int, direction: Int)
    fun itemSwiped(position: Int)
    fun itemMoved(position: Int)
}