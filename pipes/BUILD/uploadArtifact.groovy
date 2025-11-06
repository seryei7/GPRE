// Funciona de arranque del proceso de compilacion
// devuelve 0 si OK  y <>0 si ERROR
def start() {
	log.info "PRUEBAS SUBIDA ARTEFACTOS MAVEN"
	
	def resul = 0
	
	return resul; // devolveremos como error la fase de compilacion para que no continue el pipe
}

return this;