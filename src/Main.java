import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLException;

import semsim.model.collection.SemSimModel;
import semsim.model.computational.datastructures.DataStructure;
import semsim.reading.SemSimOWLreader;


public class Main {

	public static void main(String[] args) throws OWLException, CloneNotSupportedException, FileNotFoundException {
		// Get a list of annotated SemSim models to search from
		File sourceDir = new File("/Users/graham_kim/Desktop/SemSimModels");
		PrintWriter annotationList = new PrintWriter("/Users/graham_kim/Desktop/Annotations_SBML.txt");
		
		File[] directoryListing = sourceDir.listFiles();
		for(File semsimFile : directoryListing) {
			String modelName = semsimFile.getName();
			if(modelName.endsWith(".owl")) {
				
				SemSimModel semsimmodel = null;
				
				semsimmodel = new SemSimOWLreader(semsimFile).readFromFile();

				System.out.println("Extracting from: " + modelName);
				Set<DataStructure> dsSet = new HashSet<DataStructure>(semsimmodel.getAssociatedDataStructures());
				for(DataStructure ds : dsSet) {
					if(ds.getPhysicalProperty()!=null) {
						String compAnn = ds.getCompositeAnnotationAsString(false);
						String freeText = ds.getDescription();
						
						annotationList.println(compAnn + "; " + modelName);
						annotationList.println(freeText + "; " + modelName);
					}
				}
			}
		}
		annotationList.close();
	}
	
}
