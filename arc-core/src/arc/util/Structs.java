package arc.util;


import java.util.Comparator;
import java.util.Iterator;

import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Floatf;
import arc.func.Func;
import arc.func.Intf;
import arc.math.Mathf;
import arc.struct.Array;

public class Structs{

    public static <T> T[] arr(T... array){
        return array;
    }

    /** Remove all values that match this predicate. */
    public static <T> void filter(Iterable<T> iterable, Boolf<T> removal){
        filter(iterable.iterator(), removal);
    }

    /** Remove all values that match this predicate. */
    public static <T> void filter(Iterator<T> it, Boolf<T> removal){
        while(it.hasNext()){
            if(removal.get(it.next())){
                it.remove();
            }
        }
    }

    public static <T> T random(T[] array){
        if(array.length == 0) return null;
        return array[Mathf.random(array.length - 1)];
    }

    public static <T> T select(T... array){
        if(array.length == 0) return null;
        return array[Mathf.random(array.length - 1)];
    }

    /**Uses identity comparisons.*/
    public static <T> boolean contains(T[] array, T value){
        for(T t : array){
            if(t == value) return true;
        }
        return false;
    }

    public static <T> boolean contains(T[] array, Boolf<T> value){
        return find(array, value) != null;
    }

    public static <T> T find(T[] array, Boolf<T> value){
        for(T t : array){
            if(value.get(t)) return t;
        }
        return null;
    }

    public static <T> int indexOf(T[] array, T value){
        for(int i = 0; i < array.length; i++){
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }

    public static <T> int indexOf(T[] array, Boolf<T> value){
        for(int i = 0; i < array.length; i++){
            if(value.get(array[i])){
                return i;
            }
        }
        return -1;
    }

    public static <T> T[] filter(Class<T> type, T[] array, Boolf<T> value){
        Array<T> out = new Array<>(true, array.length, type);
        for(T t : array){
            if(value.get(t)) out.add(t);
        }
        return out.toArray();
    }

    public static <T> Comparator<T> comps(Comparator<T> first, Comparator<T> second){
        return (a, b) -> {
            int value = first.compare(a, b);
            if(value != 0) return value;
            return second.compare(a, b);
        };
    }

    public static <T, U> Comparator<T> comparing(Func<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator){
        return (c1, c2) -> keyComparator.compare(keyExtractor.get(c1), keyExtractor.get(c2));
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(Func<? super T, ? extends U> keyExtractor){
        return (c1, c2) -> keyExtractor.get(c1).compareTo(keyExtractor.get(c2));
    }

    public static <T> Comparator<T> comparingFloat(Floatf<? super T> keyExtractor){
        return (c1, c2) -> Float.compare(keyExtractor.get(c1), keyExtractor.get(c2));
    }

    public static <T> Comparator<T> comparingInt(Intf<? super T> keyExtractor){
        return (c1, c2) -> Integer.compare(keyExtractor.get(c1), keyExtractor.get(c2));
    }

    public static <T> void each(Cons<T> cons, T... objects){
        for(T t : objects){
            cons.get(t);
        }
    }

    public static <T> void forEach(Iterable<T> i, Cons<T> cons){
        for(T t : i){
            cons.get(t);
        }
    }

    public static <T> T findMin(T[] arr, Comparator<T> comp){
        T result = null;
        for(T t : arr){
            if(result == null || comp.compare(result, t) < 0){
                result = t;
            }
        }
        return result;
    }

    public static <T> T findMin(T[] arr, Floatf<T> proc){
        T result = null;
        float min = Float.MAX_VALUE;
        for(T t : arr){
            float val = proc.get(t);
            if(val <= min){
                result = t;
                min = val;
            }
        }
        return result;
    }

    public static <T> T findMin(Iterable<T> arr, Comparator<T> comp){
        T result = null;
        for(T t : arr){
            if(result == null || comp.compare(result, t) < 0){
                result = t;
            }
        }
        return result;
    }

    public static <T> T findMin(Iterable<T> arr, Boolf<T> allow, Comparator<T> comp){
        T result = null;
        for(T t : arr){
            if(allow.get(t) && (result == null || comp.compare(result, t) < 0)){
                result = t;
            }
        }
        return result;
    }

    public static <T> boolean inBounds(int x, int y, T[][] array){
        return x >= 0 && y >= 0 && x < array.length && y < array[0].length;
    }

    public static boolean inBounds(int x, int y, int[][] array){
        return x >= 0 && y >= 0 && x < array.length && y < array[0].length;
    }

    public static boolean inBounds(int x, int y, float[][] array){
        return x >= 0 && y >= 0 && x < array.length && y < array[0].length;
    }

    public static boolean inBounds(int x, int y, boolean[][] array){
        return x >= 0 && y >= 0 && x < array.length && y < array[0].length;
    }

    public static <T> boolean inBounds(int x, int y, int z, T[][][] array){
        return x >= 0 && y >= 0 && z >= 0 && x < array.length && y < array[0].length && z < array[0][0].length;
    }

    public static <T> boolean inBounds(int x, int y, int z, int[][][] array){
        return x >= 0 && y >= 0 && z >= 0 && x < array.length && y < array[0].length && z < array[0][0].length;
    }

    public static boolean inBounds(int x, int y, int z, int size, int padding){
        return x >= padding && y >= padding && z >= padding && x < size - padding && y < size - padding
                && z < size - padding;
    }

    public static <T> boolean inBounds(int x, int y, int width, int height){
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    // zones add begon
//    public static <T> boolean inBounds(int x, int y, Array<T> array){
//        return x >= 0 && y >= 0 && x < array.length && y < array[0].length;
//    }
    // zones add end
}
