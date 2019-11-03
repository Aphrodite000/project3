//双向链表
public class MyLinkedList implements List{
    //新结点的前去和后继，新结点的前结点的后继，新结点的后结点的前驱，头尾的特殊考虑
    //静态内部类
        private static class Node {
            private int val;
            private Node prev;
            private Node next;

            private Node(int val) {
                this.val = val;
                this.prev = null;
                this.next = null;
            }

            private Node(int val, Node prev, Node next) {
                this(val);
                this.prev = prev;
                this.next = next;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "val=" + val +
                        '}';
            }
        }

        /**
         * 记录链表的第一个结点
         */
        private Node head = null;
        /**
         * 记录链表的最后一个结点
         */
        private Node last = null;
        /**
         * 记录链表中当前元素的个数
         */
        private int size = 0;

        @Override
        public boolean add(int element) {
            return add(size, element);
        }

        /**
         * 把 element 插入到 index 位置处
         * @param index 下标
         * @param element 元素
         * @return true 代表成功、false 代表失败
         */
        @Override
        public boolean add(int index, int element) {
            // 1. 检查下标的合法性
            if (index < 0 || index > size) {
                System.out.println("下标不合法");
                return false;
            }
            //  2.1 当 index == 0 的时候，需要特殊处理 ，头插，head要变，要保持head指向头结点的属性
            if (index == 0) {
                //等号左边的head是新head,右边的head是旧head
                head = new Node(element, null, head);
                if (head.next != null) {
                    //旧结点head.next的前驱指向新结点head
                    head.next.prev = head;
                }
                //链表为null，last就指向head（新head）指向的对象
                if (size == 0) {
                    last = head;
                }
                size++;
                return true;
            }
                   //尾插 last要变，要保持last指向最后一个结点的属性
            if (index == size) {
                //新的last（新的最后一个结点）的前驱是旧的last（旧的最后一个结点）
                last = new Node(element, last, null);
                if (last.prev != null) {
                    last.prev.next = last;
                }
                if (size == 0) {
                    //链表为空
                    head = last;
                }
                size++;
                return true;
            }
            // 2. 找到 index 位置的结点node
            //    2. 如果 index 在左边，从 head 往后找，如果在右边，从 last 往前找
            Node node = getNode(index);
            // 3. 把 element 装到结点中
            // 4. node 前驱的 next = element 所在的结点，element所在的结点的前驱指向node的前驱
            // 5. element 所在结点的 next = node，node的前驱指向element所在的结点，
                   //node前驱：指的是node前面的那个结点
                   //node的前驱：指的是node本身的属性 是node.prev
            Node newNode = new Node(element, node.prev, node);
            //创建一个新结点newNode，这个新结点的前驱是旧结点的前驱，这个新结点的后继是旧结点
            //等于把新结点插入到了旧结点node前面

            //上面是让newNode指向前面和后面，现在是让前面的结点和后面的结点指向newNode,指向要是双向的
            node.prev.next = newNode;
            //node前驱的后继是新结点newNode，不是node了
            node.prev = newNode;
            //这是node的前驱变成了新结点newNode
            size++;
            // 6. size++

            return true;
        }

        /**
         * 找到 index 下标所在的结点
         * index 一定是合法的
         * 判断是从前往后找，还是从后前找
         * @param index [0, size)
         * @return
         */
        private Node getNode(int index) {
            if (index < size / 2) {
                //index位置的结点在左半边，从前面遍历找的快
                Node cur = head;
                for (int i = 0; i < index; i++) {
                    cur = cur.next;
                }

                return cur;
            } else {
                Node cur = last;
                for (int i = 0; i < size - index - 1; i++) {
                    cur = cur.prev;
                }
                return cur;
            }
        }

        @Override
        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            return getNode(index).val;
        }

        @Override
        public int set(int index, int val) {
            if (index < 0 || index >= size) {
                return -1;
            }
            Node node = getNode(index);
            int oldValue = node.val;
            node.val = val;
            return oldValue;
        }

        @Override
        public int remove(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }

            Node node = getNode(index);
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                head = node.next;
            }

            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                last = node.prev;
            }
            size--;

            return node.val;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public String toString() {
            String r = "[";
            for (Node c = head; c != null; c = c.next) {
                r += (c.val + ",");
            }
            r += "]";

            return r;
        }
}
