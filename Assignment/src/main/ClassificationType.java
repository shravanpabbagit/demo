package main;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 */
public enum ClassificationType {
//shravan
        PUBLIC("Public"),
        TOP_SECRET("Top secret"),
        SECRET("Secret"),
        DIRECTORY("directory"),
        FOLDER("folder11");
??    make changes in master
        private String printEnumValue;
        ClassificationType(String printEnumValue) {
            this.printEnumValue=printEnumValue;
            }
        public String stringValue() {
            return this.printEnumValue;
            }

}
