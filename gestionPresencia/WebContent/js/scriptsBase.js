document.addEventListener('DOMContentLoaded', function(){
	addIcons();
	
	const searchBtn = document.getElementById('searchButton');
    if (searchBtn) {
        searchBtn.addEventListener('click', function() {
            if (window._moduloActual && typeof window._moduloActual.showSearch === 'function') {
                window._moduloActual.showSearch();
            } else {
                console.warn('No hay módulo cargado o no soporta búsqueda.');
            }
        });
    }
});

function addIcons(){
	
	const items = document.querySelectorAll('i[data-icon]');
	
	items.forEach(item => {
		const iconOpen = item.getAttribute('data-icon').toUpperCase();
		const clase = item.classList;
		
		switch (iconOpen) {
			case 'MANTENIMIENTOS': 
				clase.add('fa-wrench');
			break;
			case 'CODDEN': 
				clase.add('fa-map-pin');
			break;
			case 'CUERPOS': 
				clase.add('fa-clone');
			break;
			case 'TIPOPERSONAL':
				clase.add('fa-gear');
			break;
			case 'CENTROS':
				clase.add('fa-building-o');
			break;
			case 'UNIDADES':
				clase.add('fa-sitemap');
			break;
			case 'NODOSNIVEL':
				clase.add('fa-connectdevelop');
			break;
			case 'CALENDARIOS':
				clase.add('fa-calendar-o');
			break;
			case 'HORARIOS':
				clase.add('fa-clock-o');
			break;
			case 'CALSEM':
				clase.add('fa-calendar');
			break;
			case 'HORARIOANUAL':
				clase.add('fa-dashboard');
			break;
			case 'INCIDENCIAS':
				clase.add('fa-warning');
			break;
			case 'EMPRESAS':
				clase.add('fa-black-tie');
			break;
			case 'TIPOSSERVICIO':
				clase.add('fa-empire');
			break;
			case 'EXPEDIENTES':
				clase.add('fa-file-text');
			break;
			case 'TARIFAS':
				clase.add('fa-folder-open');
			break;
			case 'CONTRATOS':
				clase.add('fa-leanpub');
			break;
			case 'PROYECTOS':
				clase.add('fa-slideshare');
			break;
			case 'SEGURIDAD':
				clase.add('fa-key');
			break;
			case 'ACCESO':
				clase.add('fa-sign-in');
			break;
			case 'GRUPOUSUARIOS':
				clase.add('fa-users');
			break;
			case 'PERSONAL':
				clase.add('fa-user');
			break;
			case 'PERSONAS':
				clase.add('fa-user-plus');
			break;
			case 'PERSONASBAJA':
				clase.add('fa-user-times');
			break;
			case 'FICHADAS':
				clase.add('fa-credit-card');
			break;
			case 'CONINCIPREV':
				clase.add('fa-stethoscope');
			break;
			case 'FICINC':
				clase.add('fa-medkit');
			break;
			case 'CONINCPREV':
				clase.add('fa-ticket');
			break;
			case 'MANUALES':
				clase.add('fa-book');
			break;
			case 'RELOJFICH':
				clase.add('fa-spinner');
			break;
			case 'HORASEXTRAS':
				clase.add('fa-clock-o');
			break;
			case 'HORASEXTRAS2':
				clase.add('fa-clock-o');
			break;
			case 'DELEGACION':
				clase.add('fa-bank');
			break;
			case 'PROCESOS':
				clase.add('fa-cogs');
			break;
			case 'PROCESO':
				clase.add('fa-hourglass-o');
			break;
			case 'SEMAFORO':
				clase.add('fa-ellipsis-v');
			break;
			case 'INFORMES':
				clase.add('fa-file-text');
			break;
			case 'IMPRESION': 
				clase.add('fa-print');
			break;
			case 'LISTADO': 
				clase.add('fa-list');
			break;
			case 'TICKETS':
				clase.add('fa-tags');
				break;
			default:
				clase.add('fa-list');
		}
	})
}

function confirmar(url, urlCancel, idCheck) {
	document.getElementById(idCheck).value = '✔';
	document.getElementById(idCheck).classList.add('checked');

	setTimeout(() => {
		if (confirm('¿Estas seguro que quieres seleccionar este area de trabajo?')) {
			window.location.href = url;
		} else {
			window.location.href = urlCancel;
		}
	}, 50);
}

function confirmarArea(i, idCheck) {
	const valorCheck = document.getElementById(idCheck.name);
	
	if(!valorCheck) {
		console.log('no se ha encontrado el id ' + idCheck.name);
	} else {
		valorCheck.value = valorCheck.value.replace('', '✔');
		document.getElementById(idCheck.name).classList.add('checked');	
	}
	
	setTimeout(() => {
		const cdNivel = document.getElementById('nivel_'+ i).value;
		const idDni = document.getElementById('ind_'+ i).value;
	
		const mensaje = '¿Estas seguro que quieres seleccionar este area de trabajo?';
	
		if (confirm(mensaje)) {
			document.forms[i].submit();
		
		} else {
			console.log('Accion cancelada por el usuario');
			valorCheck.value = valorCheck.value.replace('✔','');
			document.getElementById(idCheck.name).classList.remove('checked');	
		}
	}, 50);
	
}

function toggleMenu() {
	const submenu = document.querySelectorAll('.submenu');
	submenu.forEach(el => {
	    el.style.display = 'none';
	  });
	
    const menu = document.querySelector('.menu');
    menu.classList.toggle('collapsed');
}

function toggleSubmenu(id, button) {
    const submenu = document.getElementById(id);
    submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    
    const boton = document.getElementById(button);
    boton.textContent = (boton.textContent === '▼') ? '▲' : '▼';
    
	const menu = document.querySelector('.menu');
	
    if (menu.classList.contains('collapsed')) {
		menu.classList.toggle('collapsed');
    }
}

function toggleSubmenuHijo(id) {
    const submenuhijo = document.getElementById(id);
    submenuhijo.style.display = submenuhijo.style.display === 'block' ? 'none' : 'block';
    
	const menu = document.querySelector('.menu');
	
    if (menu.classList.contains('collapsed')) {
		menu.classList.toggle('collapsed');
    }
}

function onMenuLinkClick(event, anchorEl, idList) {
  // Evitar que el clic llegue al <li onclick="toggleSubmenu(...)">
  event.stopPropagation();

  // Quitar 'active' de todos y ponerla en el actual
  document.querySelectorAll('.textOption.active').forEach(el => el.classList.remove('active'));
  document.querySelectorAll('.active').forEach(el => el.classList.remove('active'));
  anchorEl.classList.add('active');
  document.getElementById(idList).classList.add('active');

  // Mantener abiertos los ul ancestros
  const ancestro = anchorEl.closest('.submenu, .subMenuHijo');
  if (ancestro) {
    ancestro.style.display = 'block';
  }

  // Asegurar que el contenedor de menú no está en estado 'collapsed'
  const menu = document.querySelector('.menu');
  if (menu && menu.classList.contains('collapsed')) {
    menu.classList.remove('collapsed');
  }

  // devolvemos true para que changeContent(...) se ejecute con normalidad
  return true;
}

//window.location.protocol
function incluirJSP(path, nodo) {
	const url = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + "/gestionPresencia/" + path;
	const container = document.getElementById("mainContent");

	// Mostrar spinner antes de iniciar la carga
	container.innerHTML = '<div class="spinner"></div>';

	fetch(url)
	  .then(res => res.text())
	  .then(html => {
	    if (window._moduloActual && typeof window._moduloActual.destroy === 'function') {
	      try { window._moduloActual.destroy(); } catch (e) { console.warn('Error en destroy():', e); }
	    }
	    	    
	    const prevScript = document.querySelector('script[data-dinamico="true"]');
	    if (prevScript) prevScript.remove();
	    
	    const nsName = nodo.charAt(0).toUpperCase() + nodo.slice(1);
	    const getModule = () => window[nsName];
	    const initIfReady = () => {
	      const mod = getModule();
	      if (mod && typeof mod.init === 'function') {
	        mod.init();
	        window._moduloActual = mod;
	      }
	    };
	    
	    const script = document.createElement('script');
	    script.id = nsName+'-js';
	    script.src = '/gestionPresencia/js/' + nodo + '.js';
	    script.setAttribute('data-dinamico', 'true');
	    script.onload = () => initIfReady();
	    script.onerror = () => console.error('Error cargando script:', script.src);
	    container.innerHTML = html;
	    document.body.appendChild(script);
	  })
	  .catch(err => {
	    console.error('Error cargando JSP:', err);
	    container.innerHTML = '<p>Error al cargar el contenido.</p>';
	  });
}

function changeContent(section, subSection, token, nameSec) {
    let content = '';
    let path = '';
    switch(section) {
        case '1000':
        	nodo = nodosMantenimiento(subSection);
        	if (nodo === null || nodo === undefined) {
        		content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
        	} else {
        		path = nodo+'?'+token;
            	incluirJSP(path, nameSec);
        	}
            break;
        case '1500':
            content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
            break;
        case '2000':
        	nodo = nodoSeguridad(subSection);
        	if (nodo === null || nodo === undefined) {
        		content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
        	} else {
        		path = nodo+'?'+token;
            	incluirJSP(path, nameSec);
        	}
            break;
        case '3000':
        	nodo = nodoPersonas(subSection);
        	if (nodo === null || nodo === undefined) {
        		content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
        	} else {
        		path = nodo+'?'+token;
            	incluirJSP(path, nameSec);
        	}
            break;
        case '4000':
            content = '<h1>Logout</h1><p>You have been logged out.</p>';
            break;
        case '5000':
            content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '6000':
            content = '<h1>Profile</h1><p>Adjust your profile here.</p>';
            break;
        case '7000':
        	nodo = nodoImpresion(subSection);
        	if (nodo === null || nodo === undefined) {
        		content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
        	} else {
        		path = nodo+'?'+token;
            	incluirJSP(path, nameSec);
        	}
            break;
        case '7100':
        	nodo = nodoSaldos(subSection);
        	if (nodo === null || nodo === undefined) {
        		content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
        	} else {
        		path = nodo+'?'+token;
            	incluirJSP(path, nameSec);
        	}
            break;
        case '7200':
            content = '<h1>Profile</h1><p>Adjust your profile here.</p>';
            break;
        case '7300':
            content = '<h1>Logout</h1><p>You have been logged out.</p>';
            break;
        case '7400':
            content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '7500':
            content = '<h1>Profile</h1><p>Adjust your profile here.</p>';
            break;
        case '7600':
            content = '<h1>Logout</h1><p>You have been logged out.</p>';
            break;
        case '7700':
            content = '<h1>Logout</h1><p>You have been logged out.</p>';
            break;
        default:
            content = '<h1>Main Content Area</h1><p>This is the main content of the dashboard.</p>';
    }
//    document.getElementById("mainContent").innerHTML = content;
}

function nodosMantenimiento(subSection) {
	let content = '';
    let path = '';
    switch(subSection) {
        case '1010':
        	content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
            break;
        case '1020':
            content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
            break;
        case '1030':
            content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '1040':
            content = '<h1>Profile</h1><p>Adjust your profile here.</p>';
            break;
        case '1050':
            content = '<h1>Logout</h1><p>You have been logged out.</p>';
            break;
        case '1060':
            content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '1070':
            content = '<h1>Profile</h1><p>Adjust your profile here.</p>';
            break;
        case '1080':
            content = '<h1>Logout</h1><p>You have been logged out.</p>';
            break;
        case '1090':
            content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '1100':
        	return 'mntIncidencias'
            break;
        default:
            content = '<h1>Main Content Area</h1><p>This is the main content of the dashboard.</p>';
    }
    document.getElementById("mainContent").innerHTML = content;
}

function nodoPersonas(subSection) {
	let content = '';
    let path = '';
    switch(subSection) {
        case '3010':
        	return 'mntPersonal';
            break;
        case '3020':
            content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
            break;
        case '3030':
            content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        default:
            content = '<h1>Main Content Area</h1><p>This is the main content of the dashboard.</p>';
    }
    document.getElementById("mainContent").innerHTML = content;
}

function nodoSeguridad(subSection) {
	let content = '';
    let path = '';
    switch(subSection) {
        case '2010':
        	content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '2015':
        	content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        case '2020':
            content = '<h1>Reports</h1><p>Welcome to the reports page!</p>';
            break;
        case '2030':
        	return 'mntPersonasNivel';
            break;
        case '2040':
        	content = '<h1>Settings</h1><p>Here is your settings information.</p>';
            break;
        default:
            content = '<h1>Main Content Area</h1><p>This is the main content of the dashboard.</p>';
    }
    document.getElementById("mainContent").innerHTML = content;
}

function nodoImpresion(subSection) {
	let content = '';
    let path = '';
    switch(subSection) {
        case '7050':
        	return 'gstImpresion';
            break;
        default:
            content = '<h1>Main Content Area</h1><p>This is the main content of the dashboard.</p>';
    }
    document.getElementById("mainContent").innerHTML = content;
}

function nodoSaldos(subSection) {
	let content = '';
    let path = '';
    switch(subSection) {
        case '7105':
        	return 'InfSaldoInd';
            break;
        case '7110':
        	return 'InfSaldo';
            break;
        case '7115':
        	return 'infSaldoSemanal';
            break;
        default:
            content = '<h1>Main Content Area</h1><p>This is the main content of the dashboard.</p>';
    }
    document.getElementById("mainContent").innerHTML = content;
}

//modal
const openBtn = document.getElementById('openModalBtn');
const closeBtn = document.getElementById('closeModalBtn');
const modal = document.getElementById('pdfModal');
const modalContent = modal.querySelector('.modal-content');


if (openBtn && closeBtn && modal && modalContent) {
  openBtn.addEventListener('click', () => {
    modal.style.display = 'flex';
    modal.removeAttribute('inert');
    modalContent.focus();
  });
  closeBtn.addEventListener('click', () => {
    modal.style.display = 'none';
    modal.setAttribute('inert', '');
    openBtn.focus();
  });
}

// Cerrar con Escape
document.addEventListener('keydown', (e) => {
  if (e.key === 'Escape' && modal.getAttribute('inert')) {
    closeBtn.click();
  }
});

function openPDF(url) {
    window.open(url, '_blank', 'width=800,height=600,noopener,noreferrer');
}

function validarDNI(dni) {
	  // Si empieza con 0 y tiene 10 caracteres, quitamos el 0 inicial
	  if (dni.length === 10 && dni.charAt(0) === '0') {
	      dni = dni.substring(1);
	  }

	  // Validar formato estándar: 8 números + letra
	  const dniRegex = /^[0-9]{8}[A-Za-z]$/;
	  if (!dniRegex.test(dni)) {
	      alert("Por favor, introduce un DNI válido.");
	      return false;
	  }

	  const letras = "TRWAGMYFPDXBNJZSQVHLCKE";
	  const numero = parseInt(dni.substring(0, 8), 10);
	  const letra = dni.charAt(8).toUpperCase();

	  const letraCorrecta = letras[numero % 23];
	  if (letra !== letraCorrecta) {
	      alert(`La letra del DNI no es correcta. Debería ser ${letraCorrecta}.`);
	      return false;
	  }

	  return true;
}

function validarFechas(fechaDesde, fechaHasta) {

	if (fechaDesde && fechaHasta) {
		const desde = new Date(fechaDesde);
		const hasta = new Date(fechaHasta);

		if (desde > hasta) {
			alert("La fecha 'Desde' no puede ser mayor que la fecha 'Hasta'.");
			return false;
		}
	}
	
	return true;
}

//Mapa de códigos y mensajes
const mensajesError = {
    "-1": "Código de LISTADO erróneo",
    "-2": "Error en BD. Consulte con el administrador",
    "-3": "Código de LISTADO no disponible",
    "-5": "Petición ya solicitada"
};

function procesarRespuesta(data) {
    const error = data.descripcionError || ""; // Evita null/undefined

    // Buscar si contiene alguno de los códigos definidos
    const codigoEncontrado = Object.keys(mensajesError).find(codigo => error.includes(codigo));

    if (codigoEncontrado) {
        alert(mensajesError[codigoEncontrado]);
        return;
    }

    // Si no coincide con ningún código, mensaje por defecto
    alert("Petición realizada");
}

document.addEventListener("DOMContentLoaded", function() {
	  const inicioBtn = document.getElementById("inicioBtn");
	  if (inicioBtn) {
	      inicioBtn.addEventListener("click", function(e) {
	          e.preventDefault();
	          fetch(`${contextPath}/checkSession`, { method: 'GET', credentials: 'same-origin' })
	              .then(response => response.json())
	              .then(data => {
	                  if (data.sessionActive) {
	                      window.location.href = `${contextPath}/acceso?tDni=${personaDocNacional}`;
	                  } else {
	                      alert("La sesión ha caducado. Por favor, inicia sesión nuevamente.");
	                      window.location.href = `${contextPath}/login`;
	                  }
	              }).catch(err => {
	                  console.error("Error comprobando sesión", err);
	              });
	      });
	  } else {
	      console.error("No se encontró el elemento con id 'inicioBtn'");
	  }
	});


(function() {
    var contenedor = document.getElementById("mainContent");
    if (contenedor) {
        contenedor.addEventListener("click", function(event) {
            // Si el elemento clicado es el botón Salir
            if (event.target && event.target.id === "btnSalir") {
                contenedor.innerHTML = ""; // Limpia el contenido dinámico
            }
        });
    }
})();
