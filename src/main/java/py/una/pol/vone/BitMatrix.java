package py.una.pol.vone;

import java.util.BitSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Variable;

public class BitMatrix implements Variable{
	
	private static final long serialVersionUID = 1L;
	
	private BitSet[] bitArr;
	
	private Integer rows;
	private Integer cols;
	
	/** constructor **/
    public BitMatrix(int rows, int cols)
    {
    	this.rows = rows;
    	this.cols = cols;
        bitArr = new BitSet[rows];
        for (int i = 0; i < rows; i++)
            bitArr[i] = new BitSet(cols);
    } 
    
    /** function to clear **/
    public void clear()
    {
        int rows = this.rows;
        int cols = this.cols;
        bitArr = new BitSet[rows];
        for (int i = 0; i < rows; i++)
            bitArr[i] = new BitSet(cols);       
    }
    
    /** function to clear a particular row **/
    public void clear(int r)
    {
        bitArr[r].clear();
    }
    
    /** function to clear a particular bit **/
    public void clear(int r, int c)
    {
        bitArr[r].clear(c);
    }
    
    /** function to get a particular bit **/
    public boolean get(int r, int c)
    {
        return bitArr[r].get(c);
    }
    
    /** function to set a particular bit **/
    public void set(int r, int c, boolean b)
    {
        bitArr[r].set(c, b);
    }
    
    /** function to OR two rows **/
    public void or(int r1, int r2)
    {
        bitArr[r1].or(bitArr[r2]);
    }  
    
    /** function to And two rows **/
    public void and(int r1, int r2)
    {
        bitArr[r1].and(bitArr[r2]);
    }    
    /** function to XOR two rows **/
    public void xor(int r1, int r2)
    {
        bitArr[r1].xor(bitArr[r2]);
    }
    /** function to display bitset */
    public void display()
    {
        System.out.println("\nBit Matrix : ");
        for (BitSet bs : bitArr)
               System.out.println(bs);
        System.out.println();
    } 
    
    public static boolean[][] getBinary(Variable variable) {
		if (variable instanceof BitMatrix) {
			BitMatrix bitMatrixVariable = (BitMatrix)variable;
			boolean[][] result = new boolean[bitMatrixVariable.rows][bitMatrixVariable.cols];
			
			for (int i=0; i< bitMatrixVariable.rows; i++) {
				for (int j = 0; j < bitMatrixVariable.cols; j++) {
					//System.out.println(bitMatrixVariable.get(i,j));
					result[i][j] = bitMatrixVariable.get(i,j);
				}
				
			}
			return result;
		} else {
			throw new IllegalArgumentException("Not bit matrix");
		}
	}
    
    @Override
	public BitMatrix copy() {
    	BitMatrix copy = new BitMatrix(this.rows, this.cols);
    	for (int i = 0; i < this.rows; i++) {
    		if(this.bitArr[i].cardinality() != 0){
    			copy.or(i, i);
    		}else{
    			this.bitArr[i].set(0,true);
    			copy.or(i, i);
    		}
		}
		return copy;
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.rows)
				.append(this.cols)
				.append(this.bitArr)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if ((obj == null) || (obj.getClass() != getClass())) {
			return false;
		} else {
			BitMatrix rhs = (BitMatrix)obj;
			
			return new EqualsBuilder()
					.append(rows, rhs.rows)
					.append(cols, rhs.cols)
					.append(bitArr, rhs.bitArr)
					.isEquals();
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sb.append(bitArr[i].get(j) ? "1" : "0");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public void randomize() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				set(i, j, PRNG.nextBoolean());
			}
			
		}
	}

	@Override
	public void randomize(int m, int n) {
		// TODO Auto-generated method stub
		
	}
}
