console.log("JavaScript impresión informes");

// Garantizar ejecución siempre
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', inicializarEventos);
} else {
    inicializarEventos();
}

function inicializarEventos() {
    const radiosInforme = document.querySelectorAll('input[name="selInforme"]');
    const radiosTipo = document.querySelectorAll('input[name="tipoImpresion"]');
    const btnEnviar = document.getElementById("btnEnviar");
    const btnSalir = document.getElementById("btnSalir");
    const tokenVisualizar = document.getElementById("tokenVisualizar").value;
    const tokenImprimir = document.getElementById("tokenImprimir").value;
    const spinner = document.getElementById("spinner");

    // Context path de la aplicación
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

    function validarSeleccion() {
        const informeSeleccionado = Array.from(radiosInforme).some(r => r.checked);
        const tipoSeleccionado = Array.from(radiosTipo).some(r => r.checked);
        btnEnviar.disabled = !(informeSeleccionado && tipoSeleccionado);
    }

    radiosInforme.forEach(r => r.addEventListener("change", validarSeleccion));
    radiosTipo.forEach(r => r.addEventListener("change", validarSeleccion));

    btnEnviar.addEventListener("click", function () {
        const informe = document.querySelector('input[name="selInforme"]:checked').value;
        const tipo = document.querySelector('input[name="tipoImpresion"]:checked').value;

        let endpoint = "";
        let token = "";

        endpoint = "/gestionPresencia/VisualizarInformePantalla";
        token = tokenVisualizar;
        
        if (tipo == "pantalla") {
            visualizarInformePantalla(informe, token, tipo);
        } else {
            imprimirInforme(informe, token, tipo);
        }
    });
}

/**
 * Actualiza la fila de la tabla para marcar el informe como impreso
 */
function actualizarFilaImpreso(idListado) {
    const radioSeleccionado = document.querySelector(`input[name="selInforme"][value="${idListado}"]`);
    
    if (radioSeleccionado) {
        const fila = radioSeleccionado.closest('tr');
        
        // Actualizar la columna "Impreso" (índice 3)
        const celdaImpreso = fila.cells[3];
        celdaImpreso.textContent = 'Sí';
    }
}

function visualizarInformePantalla(idListado, token, tipo) {
    const spinner = document.getElementById('spinner');
    if (spinner) spinner.style.display = 'block';

    const tokenParts = token.split('=');
    const tokenValue = tokenParts[1];

    fetch('/gestionPresencia/InformePantalla', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "X-Requested-With": "XMLHttpRequest",
            "OWASP-CSRFTOKEN": tokenValue
        },
        body: JSON.stringify({ 
            idListado: idListado,
            tipoImpresion: tipo
        })
    })
    .then(response => {
        if (!response.ok) throw new Error('Error en la respuesta del servidor: ' + response.status);
        return response.json();
    })
    .then(data => {
        if (spinner) spinner.style.display = 'none';

        if (data.success && data.url) {
            // Actualizar la fila antes de abrir la ventana
            actualizarFilaImpreso(idListado);
            
            const nuevaVentana = window.open(data.url, '_blank', 'width=1024,height=768,scrollbars=yes,resizable=yes');
            if (!nuevaVentana) {
                alert('Por favor, permita las ventanas emergentes para visualizar el informe');
            }
        } else {
            alert('Error al obtener la URL del PDF');
        }
    })
    .catch(error => {
        console.error('Error al visualizar informe:', error);
        if (spinner) spinner.style.display = 'none';
        alert('Error al visualizar el informe. Por favor, inténtelo de nuevo.');
    });
}

function imprimirInforme(idListado, token, tipo) {
    const spinner = document.getElementById('spinner');
    if (spinner) spinner.style.display = 'block';

    const tokenParts = token.split('=');
    const tokenValue = tokenParts[1];

    fetch('/gestionPresencia/InformePantalla', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "X-Requested-With": "XMLHttpRequest",
            "OWASP-CSRFTOKEN": tokenValue
        },
        body: JSON.stringify({ 
            idListado: idListado,
            tipoImpresion: tipo
        })
    })
    .then(response => {
        if (!response.ok) throw new Error('Error en la respuesta del servidor: ' + response.status);
        
        // Obtener el nombre del archivo del header Content-Disposition
        const disposition = response.headers.get('Content-Disposition');
        let filename = tipo === 'excel' ? 'informe.xls' : 'informe.pdf';
        
        if (disposition && disposition.includes('filename=')) {
            const filenameMatch = disposition.match(/filename="?(.+)"?/);
            if (filenameMatch) filename = filenameMatch[1];
        } else {
            // Si no hay header, usar el nombre del informe
            const radioSeleccionado = document.querySelector('input[name="selInforme"]:checked');
            const nombreInforme = radioSeleccionado ? radioSeleccionado.getAttribute('data-listado') : 'informe';
            filename = tipo === 'excel' ? nombreInforme + '.xls' : nombreInforme + '.pdf';
        }
        
        return response.blob().then(blob => ({ blob, filename }));
    })
    .then(({ blob, filename }) => {
        if (spinner) spinner.style.display = 'none';
        
        // Actualizar la fila después de descargar exitosamente
        actualizarFilaImpreso(idListado);
        
        // Crear un enlace temporal para descargar el archivo
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        
        // Limpiar
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
    })
    .catch(error => {
        console.error('Error al descargar informe:', error);
        if (spinner) spinner.style.display = 'none';
        alert('Error al descargar el informe. Por favor, inténtelo de nuevo.');
        document.querySelectorAll('input[name="tipoImpresion"]').forEach(r => r.checked = false);
    });
}
