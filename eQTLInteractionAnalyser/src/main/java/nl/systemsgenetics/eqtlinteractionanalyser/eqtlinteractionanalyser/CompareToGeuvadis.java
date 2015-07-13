package nl.systemsgenetics.eqtlinteractionanalyser.eqtlinteractionanalyser;

import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Patrick Deelen
 */
public class CompareToGeuvadis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
		ExpressionDataset bios = new ExpressionDataset("/Volumes/Promise_RAID/lude/InteractionZScoresMatrix-4Covariates.txt.binary");
		ExpressionDataset geuvadis = new ExpressionDataset("/Volumes/Promise_RAID/projects/BBMRI/interactionsGeuvadisRegressOut/InteractionZScoresMatrix-9Covariates.txt.binary");

		HashSet<String> covariatesReplicated = new HashSet<String>();
		HashSet<String> genesReplicated = new HashSet<String>();
		int interactionsReplicated = 0;
		
		for (Map.Entry<String, Integer> covariateEntry : bios.hashProbes.entrySet()) {
			for (Map.Entry<String, Integer> eQtlGeneEntry : bios.hashSamples.entrySet()) {

				String covariate = covariateEntry.getKey();
				String eQtlGene = eQtlGeneEntry.getKey();
				
				if(!covariate.equals("ENSG00000084072")){
					continue;
				}

				double biosInteractionZ = bios.rawData[covariateEntry.getValue()][eQtlGeneEntry.getValue()];

				if (biosInteractionZ >= 3 || biosInteractionZ <= -3) {

					Integer geuvadisCovI = geuvadis.hashProbes.get(covariate);
					Integer geuvadisGenI = geuvadis.hashSamples.get(eQtlGene);

					if (geuvadisCovI != null && geuvadisGenI != null) {

						double geuvadisInteractionZ = geuvadis.rawData[geuvadisCovI][geuvadisGenI];

						if (geuvadisInteractionZ >= 2 || geuvadisInteractionZ <= -2) {
						
							covariatesReplicated.add(covariate);
							genesReplicated.add(eQtlGene);
							interactionsReplicated++;
							
							System.out.println(covariate + "\t" + eQtlGene + "\t" + biosInteractionZ + "\t" + geuvadisInteractionZ);
							
						}

					}


				}

			}
		}
		
		System.out.println("Covariates replicated: " + covariatesReplicated.size());
		System.out.println("Genes replicated: " + genesReplicated.size());
		System.out.println("Interactions replicated: " + interactionsReplicated);
		
    }

}