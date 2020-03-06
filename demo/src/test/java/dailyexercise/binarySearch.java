package dailyexercise;

import java.util.Arrays;
import java.util.List;

public class binarySearch {
    public static void main(String[] args) {
        int[] nums = {3,2,1,4,5,6,7,8,1,10};
        System.out.println("原序列：" + Arrays.toString(nums));
        (new binarySearch()).quicksort(nums,0,nums.length-1);
        System.out.print("排序后：果果  ");
        System.out.println(Arrays.toString(nums));
    }
    public void quicksort(int[] nums,int start,int end) {
        while(end <= start) return;
        int index = start;
        int val = nums[index];
        int left = start;
        int right = end;
        while(left<right){
            while(val<=nums[right] && right>index) right--;
            if(left>=right) break;
            swap(nums,index,right);
            index = right;
            left++;
            while(val>=nums[left] && left<index) left++;
            if(left>=right) break;
            swap(nums,index,left);
            index = left;
            right--;
        }
        quicksort(nums,start,index-1);
        quicksort(nums,index+1,end);
    }
    public void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
