/**
 * Método que ejecuta la llamada al servicio rest que loguea al usuario
 */
function obtenerMenuPrincipal() {
	var urlREST = "rest/menu/menu_principal";
	$.ajax({
		url : urlREST,
		dataType : 'json',
		data : {
			format : 'json'
		},
		success : function(data) {
			console.log("Llamada al servicio rest para obtener el menú principal correcto");
			var j = 0; var arrayNodos = [];
	
			$('#ulmenu').append(pintarMenuArbol(data, arrayNodos, j));
			$('#selectMenuMovil').html(pintarMenuMovil(data));
			
			for(var i = 0; i<arrayNodos.length;i++){
				var url =  obtenerUrlERCP(arrayNodos[i][1]);
				$(arrayNodos[i][0]).click({param1:url}, function(e) {
					cargarPagina(e.data.param1, e);
				});
			}
			
			ocultarLoading();
		},
		error : function() {
			var alerta = "<div class='alert alert-danger'>";
			alerta += " Se ha producido un error al obtener el menú principal.";
			alerta += "</div>";
			$("#alertas").html(alerta);
			console.log("Error al obtener el menú principal");
		},
		type : 'GET'
	});
	
	
}




function pintarMenuArbol(nodoPadre, arrayNodos){
	var menu = "";
	if(nodoPadre!=null){		
		if(nodoPadre.listaHijos!=null){		
			for(var i=0; (nodoPadre.listaHijos.length!=undefined?i<nodoPadre.listaHijos.length:i<1); i++){
				var nodo = (nodoPadre.listaHijos.length!=undefined?nodoPadre.listaHijos[i]:nodoPadre.listaHijos);
				var texto = nodo.dsNodo;
				var a =  "<a id='nodo" + nodo.id +"'" + " href='#'" +
						" data-toggle='collapse' aria-expanded='false' class='dropdown-toggle'>" +			
						"<i class='sub_icon fa " + nodo.icono + "'></i><span>"+ texto +"</span>" +
						(nodo.listaHijos==null?"<i class='fa fa-angle-right pull-right'></i>":"<i class='fa fa-plus pull-right'></i>")+
						"</a>";
				if(nodo.listaHijos==null){
					arrayNodos[arrayNodos.length] = ["#nodo" + nodo.id, nodo.funcionalidad];
				}
				
				if(nodo.listaHijos!=null){			
					var ulInterno = "<ul class='collapse list-unstyled' id='" + nodo.funcionalidad +"' >";
				
					for(var j=0;(nodo.listaHijos.length!=undefined?j<nodo.listaHijos.length:j<1);j++){
						ulInterno+=pintarMenuArbol((nodoPadre.listaHijos.length!=undefined?nodo.listaHijos[j]:nodo.listaHijos), arrayNodos);
					}
					ulInterno+="</ul>";
					menu+="<li class='treeview'>" + a + ulInterno + "</li>";
				} else {
					menu+="<li class='treeview'>" + a + "</li>";
				}
				
			}
		} else {
			var texto = nodoPadre.dsNodo;
			var a =  "<a id='nodo" + nodoPadre.id +"'" + " href='" + (nodoPadre.listaHijos==null?obtenerUrlERCP(nodoPadre.funcionalidad) :"#" + nodoPadre.funcionalidad)+ "'" +
					" data-toggle='collapse' aria-expanded='false' class='dropdown-toggle'>" +			
					"<i class='sub_icon fa " + nodoPadre.icono + "'></i><span>"+ texto +"</span>" +
					(nodoPadre.listaHijos==null?"<i class='fa fa-angle-right pull-right'></i>":"<i class='fa fa-plus pull-right'></i>")+
					"</a>";
			menu+="<li class='treeview'>" + a + "</li>";
			arrayNodos[arrayNodos.length] = ["#nodo" + nodoPadre.id, nodoPadre.funcionalidad];
		}
	}
	return menu;
	
}


function obtenerUrlERCP(funcionalidad){
	var url = "paginas/" + funcionalidad + "/" + funcionalidad + ".html";
	return url;
}

/**
 * Metodo para pintar el menu lateral izquierdo (Menu principal)
 * 
 * @param data
 *            Parametro con los datos a pintar.
 */
function pintarMenuPrincipal(data) {

	var transform = {
		"<>" : "li",
		"class" : "treeview ${claseSeparador}",
		"html" : [ {
			"<>" : "a",
			"id": "${idHtml}",
			"href" : "${url}",
			"onclick" : function(datosItem) {
				menuClickHandler(datosItem);
			},
			"html" : [ {
				"<>" : "i",
				"class" : "sub_icon fa ${claseCSS}"
			}, {
				"<>" : "span",
				"html" : " ${texto}"
			}, {
				"<>" : "i",
				"class" : "fa fa-angle-right pull-right"
			} ]
		} ]
	};

	$('#ulmenu').json2html(data, transform, data);
}

/**
 * Metodo para pintar el menu para movil
 * 
 * @param data
 *            Parametro con los datos a pintar.
 */
function pintarMenuMovil(data) {
	var menu = "";
	if(data!=null){
		menu = "<option value='"+ data.funcionalidad + "'>"+ data.dsNodo + "</option>";
		if(data.listaHijos!=null){
			for(var i=0; i<data.listaHijos.length;i++){
				menu += pintarMenuMovil(data.listaHijos[i]);
			}
		}	
	}
	return menu;
	
}


/**
 * Método que carga el usuario logueado junto al menu correspondiente.
 * Llama al rest que obtiene el dni y los datos del usuario
 */
function cargarUsuario(){
	var usuario = $("#usuarioRegistrado");
	var imagenUsuario = $("#imagenUsuarioRegistrado");
	var urlREST = "rest/login/dni"; 
		
	if(usuario!=null && usuario!="undefined" && imagenUsuario!=null && imagenUsuario!= "undefined"){
		$.ajax({
		      url: urlREST,	      
		      dataType: 'json',
		      data: {
		         format: 'json'
		      },	      
		      success: function(data) {
		    	  if(data){
		    		  if (data.prelogin === "true") { // primera fase de login
		    			  usuario.html(crearDiv(data.dni));
		    			  imagenUsuario.attr("title",data.dni);
		    		  } else if(data.dni != null) { // segunda fase de login
		    			  console.log(data.respuesta);
		    			  var area = data.area.trim();
		    			  usuario.html(crearDiv(data.dni));
		    			  usuario.append(crearDiv(data.aplicacion + " - " + area));
		    			  usuario.append(crearDiv(data.rol));
		    			  imagenUsuario.attr("title",data.dni + " " + data.aplicacion + " " + area + " " + data.rol);
		    			  obtenerMenuPrincipal();
		    		  } else {
		    			  pintarError("#alertas", data.respuesta);
		    			  console.log( data.respuesta);
		    			  usuario.html("");
		    			  imagenUsuario.attr("title","");
		    		  }
		    	  } else {
		    		  pintarError("#alertas", "Ha ocurrido un error al obtener el usuario logado");
		    		  usuario.html("");
		    		  imagenUsuario.attr("title","");
		    	  }
		      },
		      error: function() {
		    	  ocultarLoading();
		    	  pintarError("#alertas", "Ha ocurrido un error al obtener el usuario logado");
		      },
		      type: 'GET'
		   });
	}
}

/**
 * Crea un div con el contenido
 * @param contenido
 * @returns div
 */
function crearDiv(contenido) {
	return $('<div>').text(contenido);
}

/**
 * Metodo para cargar la url indicada por parametro en el elemento "#marco".
 * 
 * @param funcionalidad
 *            Funcionalidad a cargar
 * @param event
 * 			  Evento lanzado
 */
function cargarOpcionMenuMovil(funcionalidad, event) {	
	var urlFinal = obtenerUrlERCP(funcionalidad); 
	cargarPagina(urlFinal, event);
}

/**
 * Función encargada de realizar la carga de las páginas
 * independientemente del tipo de menú cargado
 * @param url
 */
function cargarPagina(url, e) {
	limpiarPantalla();
	mostrarLoading();
	e.preventDefault();
	$.ajax({
	    url: url,
	    dataType: 'html',
	    success: function(data) {
            $('#marco').html(data);
	    },
	    error: function(xhr, error){
	    	ocultarLoading();
	    	$('#marco').html(xhr.responseText);
	    }, complete: function( response ){
	    	ocultarLoading();    
	    }
	});	
}
/**
 * Metodo para ejecutar una funcion de modo asincrono.
 * 
 * @param value
 *            Identificador.
 * @param callback
 *            Funcion.
 */
function asyncSqrt(value, callback) {
	console.log('START execution with value =', value);
	setTimeout(function() {
		callback(value, value * value);
	}, 0 | Math.random() * 1000);
}


//var cargado = 0;
//$.ajaxSetup({
//	cache : false,  
//    complete: function( response ){
//    	if(cargado == 0){
//    		$('#checkSession').load("checkSession.html");
//    		cargado = 1;
//    	} else {
//    		cargado = 0;
//    	}
//    	console.log("petición ajax completada");    
//    }
//	
//});


$(function() {
	var seleccionArea =  $.getURLParam("seleccionArea");
	
	$("#ercpjs").html("");
	mostrarLoading();
	cargarUsuario();
	$.fn.bootstrapTable.locales['es-ES'] = {
		formatAllRows : function() {
			return 'Todo';
		}
	};
	if (seleccionArea=="1"){
		var urlSeleccionArea = obtenerUrlERCP("EleccionAreaTrabajo");
		$("#marco").load(urlSeleccionArea);
	}
	
	ocultarLoading();

});