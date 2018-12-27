package move;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MoveImplTest {

    MoveImpl move;

    public MoveImplTest() {
        this.move=new MoveImpl();
    }

    Node nodeTest1;
    Node nodeTest2;

    Node nodeTest3;
    Node nodeTest4;

    @Before
    public void createFieldsAndPawns() throws IOException {
        initializeFields();
        setFieldsLocations();
    }

    private void initializeFields() throws IOException {
        nodeTest1 = new Node() {
            @Override
            protected NGNode impl_createPeer() {
                return null;
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
                return null;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
                return null;
            }
        };

        nodeTest2= new Node() {
            @Override
            protected NGNode impl_createPeer() {
                return null;
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
                return null;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
                return null;
            }
        };

        nodeTest3 = new Node() {
            @Override
            protected NGNode impl_createPeer() {
                return null;
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
                return null;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
                return null;
            }
        };

        nodeTest4 = new Node() {
            @Override
            protected NGNode impl_createPeer() {
                return null;
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
                return null;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
                return null;
            }
        };

    }

    private void setFieldsLocations() {
        GridPane.setColumnIndex(nodeTest1, 10);
        GridPane.setRowIndex(nodeTest1, 8 );
        GridPane.setColumnIndex(nodeTest2, 9);
        GridPane.setRowIndex(nodeTest2, 7);

        GridPane.setColumnIndex(nodeTest3, 14);
        GridPane.setRowIndex(nodeTest3, 10);
        GridPane.setColumnIndex(nodeTest4, 7);
        GridPane.setRowIndex(nodeTest4, 5);
    }

    @Test
    public void properSwapEq() {
        move.swap(nodeTest1, nodeTest2);
        long nodeTest2Row=GridPane.getRowIndex(nodeTest2);
        long nodeTest2Col=GridPane.getColumnIndex(nodeTest2);
        long nodeTest1Row=GridPane.getRowIndex(nodeTest1);
        long nodeTest1Col=GridPane.getColumnIndex(nodeTest1);
        Assert.assertEquals(8, nodeTest2Row);
        Assert.assertEquals(10,  nodeTest2Col);
        Assert.assertEquals(9, nodeTest1Col);
        Assert.assertEquals(7, nodeTest1Row);
    }

    @Test
    public void properSwapNotEq() {
        move.swap(nodeTest3, nodeTest4);
        Assert.assertNotEquals(10, (long) GridPane.getRowIndex(nodeTest3));
        Assert.assertNotEquals(14, (long) GridPane.getColumnIndex(nodeTest3));
        Assert.assertNotEquals(5, (long) GridPane.getRowIndex(nodeTest4));
        Assert.assertNotEquals(7, (long) GridPane.getColumnIndex(nodeTest4));
    }
}
