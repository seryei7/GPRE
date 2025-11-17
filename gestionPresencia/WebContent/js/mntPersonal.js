// /gestionPresencia/js/personal.js
(function () {
  window.MntPersonal = window.MntPersonal || {};

  // Config específica para Personal
  window.columnsPersonal = window.columnsPersonal || [];
  if (window.columnsPersonal.length === 0) {
    window.columnsPersonal.push(
      { key: 'docNacional', label: 'NIF/NIE', type: 'text', width: 'w-md' },
      { key: 'nombre', label: 'Nombre', type: 'text', width: 'w-md' },
      { key: 'apellido1', label: 'Primer Apellido', type: 'text', width: 'w-md' },
      { key: 'apellido2', label: 'Segundo Apellido', type: 'text', width: 'w-md' },
      { key: 'cdempresa', label: 'Código Empresa', type: 'text', width: 'w-sm' },
      { key: 'sexo', label: 'Sexo', type: 'text', width: 'w-xs' },
      { key: 'fcnacimiento', label: 'Fecha Nacimiento', type: 'date', width: 'w-md' },
      { key: 'cdcentro', label: 'Código Centro', type: 'text', width: 'w-sm' },
      { key: 'cdunidad', label: 'Código Unidad', type: 'text', width: 'w-sm' },
      { key: 'ntcuerpo', label: 'Código Nomenclator Cuerpo', type: 'number', width: 'w-sm' },
      { key: 'cdcuerpo', label: 'Código Cuerpo', type: 'text', width: 'w-sm' },
      { key: 'ntcodden', label: 'Código Nomenclator Denominación', type: 'number', width: 'w-sm' },
      { key: 'cdcodden', label: 'Código Puesto', type: 'number', width: 'w-sm' },
      { key: 'telcontacto', label: 'Teléfono Contacto', type: 'text', width: 'w-md' },
      { key: 'telinterno', label: 'Teléfono Interno', type: 'text', width: 'w-sm' },
      { key: 'cdtipopersonal', label: 'Código Tarjeta', type: 'number', width: 'w-sm' },
      { key: 'fcalta', label: 'Fecha Alta', type: 'text', width: 'w-sm' },
      { key: 'fcbaja', label: 'Fecha Baja', type: 'text', width: 'w-md' },
      { key: 'autorizarhoras', label: 'Autorizar Horas', type: 'text', width: 'w-sm' },
      { key: 'autorizahoras', label: 'Autoriza Horas', type: 'text', width: 'w-sm' },
      { key: 'cdcalendario', label: 'Código Calendario', type: 'number', width: 'w-sm' },
      { key: 'cdhorario', label: 'Código Horario', type: 'number', width: 'w-sm' },
      { key: 'cdtarjeta', label: 'Código Jerarquía', type: 'text', width: 'w-sm' },
      { key: 'cdcontrato', label: 'Código Contrato', type: 'number', width: 'w-sm' },
      { key: 'cdproyecto', label: 'Código Proyecto', type: 'number', width: 'w-sm' },
      { key: 'cdnivel', label: 'Nivel Acceso App', type: 'number', width: 'w-sm' },
      { key: 'cdnivelweb', label: 'Nivel Acceso Web', type: 'number', width: 'w-sm' },
      { key: 'enti_ges_ep', label: 'Entidad/Gestor', type: 'number', width: 'w-lg' },
      { key: 'prov_ep', label: 'Provincia', type: 'number', width: 'w-sm' },
      { key: 'cen_ges_ep', label: 'Centro gestor', type: 'number', width: 'w-sm' }
    );
  }

  window.MntPersonal.init = function initMntPersonal() {
    const STORAGE_KEY = 'tableStatePersonal-v1';
    const controlsGrid = document.getElementById('controlsGrid');
    const theadRow = document.getElementById('theadRow');
    const tbody = document.getElementById('tbody');
    const table = document.getElementById('dataTable');
    const toggleControls = document.getElementById('toggleControls');
    const countSpan = document.getElementById('elementCount');

    if (!controlsGrid || !theadRow || !tbody || !table) {
      console.warn('[Personal] Elementos requeridos no encontrados, init cancelado.');
      return;
    }
    if (!tbody.dataset || !tbody.dataset.json) {
      console.warn('[Personal] tbody.dataset.json no presente, init cancelado.');
      return;
    }

    const defaultVisibleColumns = window.columnsPersonal.map((col, index) => col.key === 'acciones' ? true : index < 5);

    let state = {
      visibleColumns: defaultVisibleColumns,
      sort: { index: null, dir: 'asc' },
      controlsCollapsed: true
    };

    let data = [];
    let rowElements = [];
    let originalOrder = [];

    init();

    function init() {
      loadState();
      buildControls();
      buildTable();
      applyVisibilityFromState();
//      applySortFromState();
      updateControlsToggle();
      setupEventListeners();
      desactivarBotones();
    }

    function setupEventListeners() {
      controlsGrid.addEventListener('change', onControlsChange);
      if (toggleControls) {
        toggleControls.addEventListener('click', toggleControlsSection);
      }
    }

    function buildControls() {
      const frag = document.createDocumentFragment();
      window.columnsPersonal.forEach((col, idx) => {
        if (col.key !== 'acciones') {
          const id = `col-toggle-${idx}`;
          const wrapper = document.createElement('div');
          wrapper.className = 'control-item';
          const cb = document.createElement('input');
          cb.type = 'checkbox';
          cb.id = id;
          cb.checked = state.visibleColumns[idx];
          cb.setAttribute('data-col-idx', String(idx));
          cb.ariaLabel = `Alternar columna ${col.label}`;
          const label = document.createElement('label');
          label.setAttribute('for', id);
          label.textContent = col.label;
          wrapper.append(cb, label);
          frag.appendChild(wrapper);
        }
      });
      controlsGrid.textContent = '';
      controlsGrid.appendChild(frag);
    }

    function onControlsChange(e) {
      const target = e.target;
      if (target && target.matches('input[type="checkbox"][data-col-idx]')) {
        const idx = Number(target.getAttribute('data-col-idx'));
        toggleColumn(idx, target.checked);
      }
    }

    function toggleControlsSection() {
      state.controlsCollapsed = !state.controlsCollapsed;
      controlsGrid.style.display = state.controlsCollapsed ? 'none' : 'grid';
      toggleControls.textContent = state.controlsCollapsed ? '▼' : '▲';
      toggleControls.setAttribute('aria-expanded', !state.controlsCollapsed);
      saveState();
    }

    function updateControlsToggle() {
      if (toggleControls) {
        controlsGrid.style.display = state.controlsCollapsed ? 'none' : 'grid';
        toggleControls.textContent = state.controlsCollapsed ? '▼' : '▲';
        toggleControls.setAttribute('aria-expanded', !state.controlsCollapsed);
      }
    }

    function buildTable() {
      const theadFrag = document.createDocumentFragment();
      window.columnsPersonal.forEach((col, idx) => {
        const th = document.createElement('th');
        th.className = `${col.width} col-${idx}`;
        if (col.fixed) th.classList.add('fixed-column');
        th.setAttribute('scope', 'col');
        th.setAttribute('data-type', col.type);
        th.setAttribute('data-col-idx', String(idx));
        th.setAttribute('aria-sort', 'none');

        if (col.key === 'acciones') {
          th.textContent = col.label;
        } else {
          const btn = document.createElement('button');
          btn.type = 'button';
          btn.className = 'th-btn';
          btn.setAttribute('aria-label', `Ordenar por ${col.label}`);
          btn.innerHTML = `<span>${col.label}</span><span class="sort-indicator" aria-hidden="true"></span>`;
          th.appendChild(btn);
        }
        theadFrag.appendChild(th);
      });
      theadRow.textContent = '';
      theadRow.appendChild(theadFrag);
      theadRow.addEventListener('click', onHeaderClick);

      const personalData = JSON.parse(tbody.dataset.json || '[]');
      data = personalData;

      const bodyFrag = document.createDocumentFragment();
      rowElements = data.map((row, i) => {
        const tr = document.createElement('tr');
        tr.setAttribute('data-idx', String(i));
        window.columnsPersonal.forEach((col, colIdx) => {
          const td = document.createElement('td');
          td.className = `col-${colIdx} ` + (col.key === 'acciones' ? 'align-center' : alignmentClass(col.type));
          if (col.fixed) td.classList.add('fixed-column');
          td.appendChild(renderCell(col.type, row[col.key]));
          tr.appendChild(td);
        });
        bodyFrag.appendChild(tr);
        return tr;
      });

      countSpan.textContent = rowElements.length;
      originalOrder = rowElements.slice();
      tbody.textContent = '';
      tbody.appendChild(bodyFrag);

      // Inicializar paginación genérica - const pagination = 
	  new Pagination({
	        rows: rowElements,
	        pageSize: 10,
	        paginationContainer: document.getElementById('paginationContainer'),
	        firstPageBtn: document.getElementById('firstPage'),
	        prevPageBtn: document.getElementById('prevPage'),
	        nextPageBtn: document.getElementById('nextPage'),
	        lastPageBtn: document.getElementById('lastPage'),
	        pageInfo: document.getElementById('pageInfo'),
	        pageSizeSelector: document.getElementById('pageSizeSelector')
	   });
      
//      pagination.updatePagination();
    }

    function renderCell(type, value) {
      const span = document.createElement('span');
      if (type === 'currency') span.textContent = value;
      else if (type === 'percent') span.textContent = `${value}%`;
      else if (type === 'boolean') {
        span.textContent = value ? 'Sí' : 'No';
        span.className = 'badge';
      } else span.textContent = String(value ?? '');
      return span;
    }

    function alignmentClass(type) {
      switch (type) {
        case 'number':
        case 'currency':
        case 'percent':
          return 'align-right';
        case 'date':
        case 'boolean':
          return 'align-center';
        default:
          return 'align-left';
      }
    }

    function onHeaderClick(e) {
      const btn = e.target && (e.target.closest && e.target.closest('.th-btn'));
      if (!btn) return;
      const th = btn.closest('th');
      const idx = Number(th.getAttribute('data-col-idx'));
      let dir = 'asc';
      if (state.sort.index === idx) {
        dir = state.sort.dir === 'asc' ? 'desc' : 'asc';
      }
      sortByColumn(idx, dir);
    }

    function sortByColumn(index, dir = 'asc') {
      const ths = [...theadRow.children];
      const th = ths[index];
      const type = th.getAttribute('data-type');
      const rows = rowElements.map(tr => {
        const cell = tr.children[index];
        const text = (cell.textContent || '').trim();
        return { tr, v: text, idx: Number(tr.getAttribute('data-idx')) };
      });
      rows.sort((a, b) => compareValues(a.v, b.v, type, dir) || a.idx - b.idx);
      rowElements = rows.map(r => r.tr);
      state.sort = { index, dir };
      saveState();
      ths.forEach(th => th.setAttribute('aria-sort', 'none'));
      th.setAttribute('aria-sort', dir);
    }

    function compareValues(a, b, type, dir) {
      if (type === 'number') {
        const valA = parseFloat(a) || 0;
        const valB = parseFloat(b) || 0;
        return dir === 'asc' ? valA - valB : valB - valA;
      }
      return dir === 'asc'
        ? a.localeCompare(b, undefined, { numeric: true, sensitivity: 'base' })
        : b.localeCompare(a, undefined, { numeric: true, sensitivity: 'base' });
    }

    function toggleColumn(idx, visible) {
      state.visibleColumns[idx] = visible;
      applyVisibilityFromState();
      saveState();
    }

    function applyVisibilityFromState() {
      const cols = [...table.querySelectorAll(`thead th, tbody td`)];
      window.columnsPersonal.forEach((_, idx) => {
        const show = state.visibleColumns[idx];
        const cells = cols.filter(el => el.classList.contains(`col-${idx}`));
        cells.forEach(cell => {
          cell.style.display = show ? '' : 'none';
        });
      });
    }

    function loadState() {
      try {
        const raw = localStorage.getItem(STORAGE_KEY);
        if (raw) {
          const savedState = JSON.parse(raw);
          Object.keys(state).forEach(key => {
            if (savedState[key] !== undefined) {
              state[key] = savedState[key];
            }
          });
        }
      } catch (e) {
        console.warn('Error loading saved state:', e);
      }
    }

    function saveState() {
      try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
      } catch (e) {
        console.warn('Error saving state:', e);
      }
    }

    function desactivarBotones() {
      document.getElementById('insertButton').disabled = true;
      document.getElementById('deleteButton').disabled = true;
      document.getElementById('printButton').disabled = true;
      document.getElementById('newsButton').disabled = true;
      document.getElementById('keyButton').disabled = true;
      document.getElementById('searchButton').disabled = true;
    }
  };
})();
