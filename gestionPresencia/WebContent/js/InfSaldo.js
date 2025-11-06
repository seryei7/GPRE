console.log("JavaScript saldo semanal - Árbol jerárquico");

(function() {
	// Usar setTimeout para asegurar que el DOM está completamente cargado
	setTimeout(function() {
		console.log('Inicializando árbol jerárquico');
		inicializarArbol();
	}, 100);
})();

function inicializarArbol() {
	// Ocultar todas las sublistas al inicio
	var sublistas = document.querySelectorAll('.child-list');
	console.log('Sublistas encontradas:', sublistas.length);
	for (var i = 0; i < sublistas.length; i++) {
		sublistas[i].style.display = 'none';
	}

	// Asegurar que todos los items con hijos tengan la clase collapsed
	var itemsConHijos = document.querySelectorAll('li.parent-item');
	for (var i = 0; i < itemsConHijos.length; i++) {
		if (!itemsConHijos[i].classList.contains('collapsed')) {
			itemsConHijos[i].classList.add('collapsed');
		}
	}

	// Para los li que tienen toggle-btn pero no tienen clase parent-item,
	// añadirla
	var liConToggle = document.querySelectorAll('li:has(.toggle-btn-saldo)');
	if (liConToggle.length === 0) {
		// Fallback para navegadores que no soportan :has()
		var todosLosLi = document.querySelectorAll('li');
		for (var i = 0; i < todosLosLi.length; i++) {
			var tieneToggle = false;
			var children = todosLosLi[i].children;
			for (var j = 0; j < children.length; j++) {
				if (children[j].classList
						&& children[j].classList.contains('toggle-btn-saldo')) {
					tieneToggle = true;
					break;
				}
			}
			if (tieneToggle && !todosLosLi[i].classList.contains('parent-item')) {
				todosLosLi[i].classList.add('parent-item');
				todosLosLi[i].classList.add('collapsed');
			}
		}
	} else {
		for (var i = 0; i < liConToggle.length; i++) {
			if (!liConToggle[i].classList.contains('parent-item')) {
				liConToggle[i].classList.add('parent-item');
				liConToggle[i].classList.add('collapsed');
			}
		}
	}

	// Configurar botones de toggle
	configurarBotonesToggle();

	// Configurar checkboxes jerárquicos
	configurarCheckboxes();
}

function configurarBotonesToggle() {
	var toggleButtons = document.querySelectorAll('.toggle-btn-saldo');
	console.log('Botones toggle encontrados:', toggleButtons.length);

	for (var i = 0; i < toggleButtons.length; i++) {
		(function(button) {
			button.addEventListener('click', function(e) {
				console.log('Click en botón toggle');
				e.preventDefault();
				e.stopPropagation();

				// Buscar el li padre inmediato
				var listItem = this.parentElement;
				console.log('ListItem:', listItem);

				// Buscar la lista de hijos directa (solo el primer nivel)
				var childList = null;
				var children = listItem.children;
				for (var j = 0; j < children.length; j++) {
					if (children[j].tagName.toLowerCase() === 'ul'
							&& children[j].classList.contains('child-list')) {
						childList = children[j];
						break;
					}
				}

				console.log('ChildList encontrada:', childList);

				if (childList) {
					var isCollapsed = listItem.classList.contains('collapsed');
					console.log('Estado actual - Collapsed:', isCollapsed);

					if (isCollapsed) {
						// Expandir
						listItem.classList.remove('collapsed');
						listItem.classList.add('expanded');
						this.textContent = '-';
						childList.style.display = 'block';
						console.log('Expandido');
					} else {
						// Colapsar
						listItem.classList.remove('expanded');
						listItem.classList.add('collapsed');
						this.textContent = '+';
						childList.style.display = 'none';
						console.log('Colapsado');
					}
				} else {
					console.log('No se encontró childList');
				}
			});
		})(toggleButtons[i]);
	}
}

function configurarCheckboxes() {
	var todosLosCheckboxes = document
			.querySelectorAll('input[type="checkbox"]');
	console.log('Checkboxes encontrados:', todosLosCheckboxes.length);

	for (var i = 0; i < todosLosCheckboxes.length; i++) {
		(function(checkbox) {
			checkbox.addEventListener('change', function() {
				console.log('Checkbox cambiado:', this);

				if (this.classList.contains('parent-checkbox')) {
					// Es un padre: marcar/desmarcar todos sus descendientes
					marcarDescendientes(this);
				}
				// Actualizar estado de todos los ancestros
				actualizarAncestros(this);
			});
		})(todosLosCheckboxes[i]);
	}
}

function marcarDescendientes(checkbox) {
	var listItem = checkbox.parentElement;
	while (listItem && listItem.tagName.toLowerCase() !== 'li') {
		listItem = listItem.parentElement;
	}

	if (!listItem) {
		console.log('No se encontró el listItem padre');
		return;
	}

	// Buscar la childList directa
	var childList = null;
	var children = listItem.children;
	for (var i = 0; i < children.length; i++) {
		if (children[i].tagName.toLowerCase() === 'ul'
				&& children[i].classList.contains('child-list')) {
			childList = children[i];
			break;
		}
	}

	if (childList) {
		var descendientes = childList
				.querySelectorAll('input[type="checkbox"]');
		console
				.log('Marcando/desmarcando descendientes:',
						descendientes.length);
		for (var i = 0; i < descendientes.length; i++) {
			descendientes[i].checked = checkbox.checked;
			descendientes[i].indeterminate = false;
		}
	}
}

function actualizarAncestros(checkbox) {
	var listItem = checkbox.parentElement;
	while (listItem && listItem.tagName.toLowerCase() !== 'li') {
		listItem = listItem.parentElement;
	}

	if (!listItem)
		return;

	var parentUl = listItem.parentElement;
	if (!parentUl || parentUl.tagName.toLowerCase() !== 'ul')
		return;

	// Buscar el li padre que contiene este ul
	var parentLi = parentUl.parentElement;
	while (parentLi && parentLi.tagName.toLowerCase() !== 'li') {
		parentLi = parentLi.parentElement;
	}

	if (parentLi) {
		// Buscar el checkbox del padre (puede ser el primer input type
		// checkbox)
		var parentCheckbox = null;
		var children = parentLi.children;
		for (var i = 0; i < children.length; i++) {
			if (children[i].tagName.toLowerCase() === 'input'
					&& children[i].type === 'checkbox') {
				parentCheckbox = children[i];
				break;
			}
		}

		if (parentCheckbox) {
			// Obtener todos los checkboxes hijos directos (solo del primer
			// nivel del ul)
			var hijosDirectos = [];
			var childrenUl = parentUl.children;
			for (var i = 0; i < childrenUl.length; i++) {
				if (childrenUl[i].tagName.toLowerCase() === 'li') {
					var liChildren = childrenUl[i].children;
					for (var j = 0; j < liChildren.length; j++) {
						if (liChildren[j].tagName.toLowerCase() === 'input'
								&& liChildren[j].type === 'checkbox') {
							hijosDirectos.push(liChildren[j]);
							break;
						}
					}
				}
			}

			var todosChecked = true;
			var algunoChecked = false;

			for (var i = 0; i < hijosDirectos.length; i++) {
				if (hijosDirectos[i].checked || hijosDirectos[i].indeterminate) {
					algunoChecked = true;
				}
				if (!hijosDirectos[i].checked) {
					todosChecked = false;
				}
			}

			if (todosChecked && hijosDirectos.length > 0) {
				parentCheckbox.checked = true;
				parentCheckbox.indeterminate = false;
			} else if (algunoChecked) {
				parentCheckbox.checked = false;
				parentCheckbox.indeterminate = true;
			} else {
				parentCheckbox.checked = false;
				parentCheckbox.indeterminate = false;
			}

			// Recursivamente actualizar los ancestros superiores
			actualizarAncestros(parentCheckbox);
		}
	}
}

// Funciones auxiliares para debugging
function expandirTodo() {
	var items = document.querySelectorAll('li.collapsed');
	for (var i = 0; i < items.length; i++) {
		items[i].classList.remove('collapsed');
		items[i].classList.add('expanded');

		var btn = null;
		var children = items[i].children;
		for (var j = 0; j < children.length; j++) {
			if (children[j].classList.contains('toggle-btn-saldo')) {
				btn = children[j];
				break;
			}
		}
		if (btn)
			btn.textContent = '-';

		var childList = null;
		for (var j = 0; j < children.length; j++) {
			if (children[j].classList.contains('child-list')) {
				childList = children[j];
				break;
			}
		}
		if (childList)
			childList.style.display = 'block';
	}
	console.log('Todos los nodos expandidos');
}

function colapsarTodo() {
	var items = document.querySelectorAll('li.expanded, li.parent-item');
	for (var i = 0; i < items.length; i++) {
		items[i].classList.remove('expanded');
		items[i].classList.add('collapsed');

		var btn = null;
		var children = items[i].children;
		for (var j = 0; j < children.length; j++) {
			if (children[j].classList.contains('toggle-btn-saldo')) {
				btn = children[j];
				break;
			}
		}
		if (btn)
			btn.textContent = '+';

		var childList = null;
		for (var j = 0; j < children.length; j++) {
			if (children[j].classList.contains('child-list')) {
				childList = children[j];
				break;
			}
		}
		if (childList)
			childList.style.display = 'none';
	}
	console.log('Todos los nodos colapsados');
}

// Exponer funciones globalmente para debugging
window.expandirTodo = expandirTodo;
window.colapsarTodo = colapsarTodo;

// === NUEVA FUNCIÓN PARA ENVIAR DATOS POR AJAX ===
document.getElementById("btnInforme").addEventListener("click", function() {
	const token = this.dataset.token;
    const url = "/gestionPresencia/InformeSaldoSemanal";
    const tokenParts = token.split('=');
    const tokenValue = tokenParts[1];
    const fechaHasta = document.getElementById("fechaHasta").value;
    const fechaDesde = document.getElementById("fechaDesde").value;
    
    const seleccionados = {
    		codigoEntidad: document.getElementById("codigoEntidad").value || "",
    		codigoProvincia: document.getElementById("codigoProvincia").value || "",
			codigoCgpe: document.getElementById("codigoCgpe").value || "",
			fechaDesde: fechaDesde || "",
			fechaHasta: fechaHasta || "",
			centros: [],
			unidades: [],
			empresas: [],
			tipoPersonal: []
  };
    
    if (!validarFechas(fechaDesde, fechaHasta)) {
		return; // Sale del método si las fechas no son válidas
	}

  document.querySelectorAll('input[name="centros"]:checked').forEach(cb => seleccionados.centros.push(cb.value));
  document.querySelectorAll('input[name="unidades"]:checked').forEach(cb => seleccionados.unidades.push(cb.value));
  seleccionados.empresas = Array.from(document.querySelectorAll('input[name="empresas"]:checked')).filter(cb => cb.value !== "AsistenciaTecnica") // Filtra el primer valor
  	.map(cb => cb.value); // Extrae solo el valor
  seleccionados.tipoPersonal = Array.from(document.querySelectorAll('input[name="tipoPersonal"]:checked')).filter(cb => cb.value !== "AdministracionPublica") // Filtra el primer valor
	.map(cb => cb.value); // Extrae solo el valor

  console.log("Datos a enviar:", seleccionados);
  
  //Mostrar spinner
  document.getElementById("spinner").style.display = "block";

  fetch(url, {
      method: "POST",
      headers: {
          "Content-Type": "application/json",
          "X-Requested-With": "XMLHttpRequest",
          "OWASP-CSRFTOKEN": tokenValue
      },
      body: JSON.stringify(seleccionados)
  })
  .then(response => {
      if (!response.ok) throw new Error("Error en la respuesta del servidor");
      return response.json();
  })
  .then(data => {
      console.log("Respuesta del servidor:", data);
      document.getElementById("spinner").style.display = "none";

      procesarRespuesta(data);
  })
  .catch(error => {
	  document.getElementById("spinner").style.display = "none";
      console.error("Error en la petición:", error);
      alert("Error al generar el informe");
  });
});

function comprobarSeleccion() {
	  const centros = document.querySelectorAll('input[name="centros"]:checked').length;
	  const unidades = document.querySelectorAll('input[name="unidades"]:checked').length;
	  const empresas = document.querySelectorAll('input[name="empresas"]:checked').length;
	  const tipoPersonal = document.querySelectorAll('input[name="tipoPersonal"]:checked').length;

	  const btn = document.getElementById("btnInforme");

	  // Si centros y unidades están vacíos, o empresas y tipoPersonal están
		// vacíos → deshabilitar
	  if ((centros === 0 && unidades === 0) || (empresas === 0 && tipoPersonal === 0)) {
	      btn.disabled = true;
	  } else {
	      btn.disabled = false;
	  }
}

// Escuchar cambios en todos los checkboxes relevantes
//document.addEventListener("DOMContentLoaded", () => {
//    document.querySelectorAll('input[name="centros"], input[name="unidades"], input[name="empresas"], input[name="tipoPersonal"]')
//        .forEach(cb => cb.addEventListener("input", comprobarSeleccion)); // input se dispara inmediatamente
//});


// Comprobar al cargar la página
//comprobarSeleccion();

console.log('Script cargado completamente. Usa expandirTodo() o colapsarTodo() para probar.');
