console.log("javascript saldo individual");

document.getElementById("btnConsultar").addEventListener("click", function() {
    const token = this.dataset.token;
    const consultarUrl = "/gestionPresencia/ConsultarSaldoInd";
    const tokenParts = token.split('=');
    const tokenValue = tokenParts[1];
    const dni = document.getElementById("nif").value;
    
    const fechaHasta = document.getElementById("fechaHasta").value;
    const fechaDesde = document.getElementById("fechaDesde").value;
    
    if (!validarDNI(dni)) {
		return; // Sale del método si DNI no es válido
    }

    if (!validarFechas(fechaDesde, fechaHasta)) {
		return; // Sale del método si las fechas no son válidas
    }
    
    document.getElementById("spinner").style.display = "block";
    
    fetch(consultarUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-Requested-With": "XMLHttpRequest",
            "OWASP-CSRFTOKEN": tokenValue
        },
        body: JSON.stringify({ dni: dni })
    })
    .then(res => {
        if (!res.ok) throw new Error("Error en la respuesta del servidor");
        return res.json(); // 🔹 Ahora esperamos JSON
    })
    .then(data => {
    	if (data.existe == "true") {
    		document.getElementById("spinner").style.display = "none";
            document.getElementById("nombre").value = data.nombre || "";
            document.getElementById("empresa").value = data.empresa || "";
            document.getElementById("contrato").value = data.contrato || "";
            document.getElementById("categoria").value = data.categoria || "";
            document.getElementById("proyecto").value = data.proyecto || "";
            document.getElementById("centro").value = data.centro || "";
            document.getElementById("unidad").value = data.unidad || "";
            document.getElementById("entiGesEp").value = data.entiGesEp || "";
            document.getElementById("provEp").value = data.provEp || "";
            document.getElementById("cenGesEp").value = data.cenGesEp || "";
            document.getElementById("idTipoPersonal").value = data.idTipoPersonal || "";
            document.getElementById("idCentro").value = data.idCentro || "";
            document.getElementById("idUnidad").value = data.idUnidad || "";
            document.getElementById("idCifNif").value = data.idCifNif || "";
            document.querySelectorAll("#fechaDesde, #fechaHasta, #nombre, #empresa, #contrato, #categoria, #proyecto, #centro, #unidad, #btnInforme")
                .forEach(el => el.removeAttribute("disabled"));
		} else {
			alert("No tiene permisos para consultar el dni solicitado.");
			document.getElementById("spinner").style.display = "none";
		}
        
    })
    .catch(err => {
        document.getElementById("spinner").style.display = "none";
        console.error(err);
        alert(err.message);
    });
});

document.getElementById("btnInforme").addEventListener("click", function () {
	  const token = this.dataset.token;
	  const informeUrl = "/gestionPresencia/InformeSaldoInd";
	  const tokenParts = token.split('=');
	  const tokenValue = tokenParts[1];
	  
	  // Datos del formulario
	  const dni = document.getElementById("nif").value.trim();
	  const fechaDesde = document.getElementById("fechaDesde").value;
	  const fechaHasta = document.getElementById("fechaHasta").value;
	  const nombre = document.getElementById("nombre").value;
	  const empresa = document.getElementById("empresa").value;
	  const contrato = document.getElementById("contrato").value;
	  const categoria = document.getElementById("categoria").value;
	  const proyecto = document.getElementById("proyecto").value;
	  const centro = document.getElementById("centro").value;
	  const unidad = document.getElementById("unidad").value;
	  const entiGesEp = document.getElementById("entiGesEp").value;
	  const provEp = document.getElementById("provEp").value;
	  const cenGesEp = document.getElementById("cenGesEp").value;
	  const idTipoPersonal = document.getElementById("idTipoPersonal").value;
	  const idCentro = document.getElementById("idCentro").value;
	  const idUnidad = document.getElementById("idUnidad").value;
	  const idCifNif = document.getElementById("idCifNif").value;

	  // Validación básica de DNI
	  if (!validarDNI(dni)) {
		  return; // Sale del método si DNI no es válido
	  }

	  if (!validarFechas(fechaDesde, fechaHasta)) {
		  return; // Sale del método si las fechas no son válidas
	  }

	  // Mostrar spinner
	  document.getElementById("spinner").style.display = "block";

	  // Construir el objeto con todos los parámetros
	  const payload = {
	      dni: dni,
	      fechaDesde: fechaDesde,
	      fechaHasta: fechaHasta,
	      nombre: nombre,
	      empresa: empresa,
	      contrato: contrato,
	      categoria: categoria,
	      proyecto: proyecto,
	      centro: centro,
	      unidad: unidad,
	      entiGesEp: entiGesEp,
	      provEp: provEp,
	      cenGesEp: cenGesEp,
	      idTipoPersonal: idTipoPersonal,
	      idCentro: idCentro,
	      idUnidad: idUnidad,
	      idCifNif: idCifNif
	  };

	  fetch(informeUrl, {
	      method: "POST",
	      headers: {
	          "Content-Type": "application/json",
	          "X-Requested-With": "XMLHttpRequest",
	          "OWASP-CSRFTOKEN": tokenValue
	      },
	      body: JSON.stringify(payload)
	  })
	  .then(res => {
		  if (!res.ok) throw new Error("Error en la respuesta del servidor");
	      return res.json();
	  })
	  .then(data => {
	      document.getElementById("spinner").style.display = "none";

	      procesarRespuesta(data);
	  })
	  .catch(err => {
	      document.getElementById("spinner").style.display = "none";
	      console.error(err);
	      alert("Error al solicitar el informe: " + err.message);
	  });
	});
