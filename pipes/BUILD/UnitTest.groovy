// Funciona de arranque del proceso de compilacion
// devuelve 0 si OK  y <>0 si ERROR
def start() {
	log.info "PRUEBAS UNITARIAS PROYECTO"
	
	def resul = 0
	
	log.info "SCRIPT EVALUACION DE SONAR PROYECTO"
	
	log.info "Resultado de ejecucion PROSA.UnitTest.start(): ${resul}"
	return resul; // devolveremos como error la fase de compilacion para que no continue el pipe
	
}

return this;