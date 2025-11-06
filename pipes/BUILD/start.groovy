import frmwork.tools.Configuracion
// Funcion de arranque del proceso de compilacion
// devuelve 0 si OK  y <>0 si ERROR
def start() {

	log.info "[BUILD-start.groovy] PACKAGE"
	
	def conf = Configuracion.instance
	def nuevoTag = conf.versionEjecucion.toDeployArtifact()
	
	sh "pwd"
	sh "ls -la"
	configFileProvider(
			[configFile(fileId: 'backend-settings', variable: 'MAVEN_SETTINGS')]){
		sh "mvn versions:set -DnewVersion=${nuevoTag} -s $MAVEN_SETTINGS "
		sh "mvn package -DskipTests -s  $MAVEN_SETTINGS"

	}
	//actualizado el nombre de la carpeta de SIPDatosPersonales a GPRE0000
	sh "ls -la ${WORKSPACE}/GPRE/target"
	sh "cp ${WORKSPACE}/GPRE/target/*.ear ${WORKSPACE}/a/GPRE.ear"

	return 0;
}

return this;




