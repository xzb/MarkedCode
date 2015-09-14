private static <AnyType extends Comparable<? super AnyType>> AnyType median3(AnyType a[], int left, int right)
{
    int center = ( left + right ) / 2;
    if( a[ center ].compareTo(a[ left ]) < 0 )
        swapReferences( a, left, center );  			// swaps elements
    if( a[ right ].compareTo(a[ left ] < 0 )
        swapReferences( a, left, right );
    if( a[ right ].compareTo(a[ center ] < 0 )
        swapReferences( a, center, right );

    // Place pivot at position right – 1, since a[right] is in correct place
    swapReferences( a, center, right-1 );  
    return a[ right - 1 ];
}

private static <AnyType extends Comparable<? super AnyType>> void quicksort( AnyType a[], int left, int right )
{
    if( left + CUTOFF <= right ) {
        AnyType pivot = median3( a, left, right );  	// get pivot as median of 3
        int i = left, j = right - 1; 					// i starts at left, j starts at right – 1 (because of pivot)
        for( ; ; ) {
            while( a[ ++i ].compareTo(pivot) < 0 ) { }  // move i right while < pivot
            while( a[ --j ].compareTo(pivot) > 0 ) { }  // move j left while > pivot
            if( i < j )    								// if i still left of j
                swapReferences( a, i, j );          	// swap the pair 
            else break;                  				// break when i is no longer left of j
        }
        swapReferences( a, i, right - 1 ] );     		// Restore pivot
        quicksort( a, left, i - 1 );       				// Sort elements in left partition (elements < pivot)
        quicksort( a, i + 1, right );    				// Sort elements in right partition (elements > pivot)
    }
    else  												// list size has reached cutoff point, just do insertion sort
        insertionSort( a, left, right );
}

