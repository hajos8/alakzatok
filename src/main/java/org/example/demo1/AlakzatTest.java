package org.example.demo1;

import org.junit.Test;

public class AlakzatTest {
    @Test
    public void testAddAlakzat() {
        AlakzatController.isTestIsRunning = true;

        AlakzatController.TestColor = "Piros";
        AlakzatController.TestShape = "Kör";
        AlakzatController.TestIndex = 0;

        AlakzatController controller = new AlakzatController();

        controller.add();
        assert AlakzatController.alakzatokList.size() == 1;

        controller.delete();
        assert AlakzatController.alakzatokList.isEmpty();

        controller.initialize(null, null);
        controller.save();

    }

}
