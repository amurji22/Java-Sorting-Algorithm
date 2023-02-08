package se2203.amurji22_assignment1;

public class QuickSort implements SortingStrategy{
    @Override
    public void run() {
    }
    private SortingHubController controller;
    public QuickSort(SortingHubController controller) {
        this.controller = controller;
    }
    @Override
    public void sort(int a[], int start, int end) /* a[] = array to be sorted, start = Starting index, end = Ending index */
    {
        if (start < end) {
            int p = partition(a, start, end); //p is the partitioning index

            sort(a, start, p - 1);

            sort(a, p + 1, end);
        }
    }
    int partition (int a[], int start, int end)
    {
        int pivot = a[end]; // pivot element
        int i = (start - 1);

        for (int j = start; j <= end - 1; j++)
        {
            // If current element is smaller than the pivot
            if (a[j] < pivot)
            {
                i++; // increment index of smaller element
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                try {
                    Thread.sleep(30);
                    controller.updateGraph(a);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        int t = a[i+1];
        a[i+1] = a[end];
        a[end] = t;
        return (i + 1);
    }
}
