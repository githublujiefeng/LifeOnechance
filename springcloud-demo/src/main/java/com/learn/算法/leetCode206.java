package com.learn.算法;

public class leetCode206 {

    public static void main(String[] args) {
        Node node1 = new Node(3);
        Node node2 = new Node(2);
        Node node3 = new Node(0);
        Node node4 = new Node(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        Node res = new leetCode206().detectCycle(node1);
        System.out.println(res);
    }


    public Node detectCycle(Node head) {
        Node fast = head;
        Node low = head;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            low = low.next;
            if(fast==low){
                break;
            }
        }
        if(fast==null || fast.next==null){
            return null;
        }
        Node pre =head;
        while(pre != fast){
            pre = pre.next;
            fast = fast.next;
        }
        return pre;
    }
    public Node reverseList1(Node head) {
        if(head.next==null){
            return head;
        }
        Node node = reverseList(head.next);
        head.next.next = head;
        head.next =null;
        return node;
    }
    public boolean isPalindrome(Node head) {
        Node nodeFirst = head;
        Node nodeSecond = head;
        while(nodeSecond.next!=null && nodeSecond.next.next!=null){
            nodeFirst = nodeFirst.next;
            nodeSecond = nodeSecond.next.next;
        }
        Node trunList = reverseList(nodeFirst.next);
        Node p = trunList;
        nodeSecond= head;
        boolean res = true;
        while(res && p!=null){
            if(nodeSecond.val!=p.val){
                res = false;
            }
            nodeSecond = nodeSecond.next;
            p = p.next;
        }
        nodeFirst.next = reverseList(trunList);
        return res;
    }
    Node reverseList(Node node){
        Node pre =null;
        Node cur = node;
        while(cur!=null){
            Node temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
