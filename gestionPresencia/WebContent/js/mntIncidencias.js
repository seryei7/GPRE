// /gestionPresencia/js/incidencias.js
(function () {
  // Evitar redefinir el namespace
  window.MntIncidencias = window.MntIncidencias || {};

  // Config específica para Incidencias
  window.columnsIncidencias = window.columnsIncidencias || [];
  if (window.columnsIncidencias.length === 0) {
    window.columnsIncidencias.push(
      { key: 'ntincidencia', label: 'Nº incidencia', type: 'number', width: 'w-sm' },
      { key: 'idincidencia', label: 'ID incidencia', type: 'number', width: 'w-sm' },
      { key: 'dsincidencia', label: 'Descripción', type: 'text', width: 'w-lg' },
      { key: 'dsincidenciared', label: 'Descripción (resumida)', type: 'text', width: 'w-md' },
      { key: 'acumula', label: 'Acumula', type: 'number', width: 'w-sm' },
      { key: 'absentismo', label: 'Absentismo', type: 'text', width: 'w-md' },
      { key: 'productividad', label: 'Productividad', type: 'text', width: 'w-md' },
      { key: 'diashoras', label: 'Días/horas', type: 'text', width: 'w-md' },
      { key: 'reloj', label: 'Reloj', type: 'text', width: 'w-sm' },
      { key: 'apertautom', label: 'Apertura automática', type: 'text', width: 'w-md' },
      { key: 'lapsoentrafija', label: 'Lapso entrada fija', type: 'number', width: 'w-md' },
      { key: 'lapsoentrafijaasoc', label: 'Lapso entrada fija asociada', type: 'number', width: 'w-md' },
      { key: 'aperautmn', label: 'Apertura automática (min)', type: 'text', width: 'w-md' },
      { key: 'aperautmnasoc', label: 'Apertura automática (min) asoc.', type: 'number', width: 'w-md' },
      { key: 'cierreautom', label: 'Cierre automático', type: 'text', width: 'w-md' },
      { key: 'lapsosalfija', label: 'Lapso salida fija', type: 'number', width: 'w-md' },
      { key: 'lapsosalfijaasoc', label: 'Lapso salida fija asociada', type: 'number', width: 'w-md' },
      { key: 'cierreautmn', label: 'Cierre automático (min)', type: 'text', width: 'w-md' },
      { key: 'cierreautmnasoc', label: 'Cierre automático (min) asoc.', type: 'number', width: 'w-md' },
      { key: 'cdincidenciaasociada', label: 'Incidencia asociada', type: 'number', width: 'w-md' },
      { key: 'numdiasincidenciaasociada', label: 'Nº días incidencia asociada', type: 'number', width: 'w-md' },
      { key: 'fcinicio', label: 'Fecha inicio', type: 'number', width: 'w-md' },
      { key: 'fcfin', label: 'Fecha fin', type: 'number', width: 'w-md' }
    );
  }

  window.MntIncidencias.init = function initMntIncidencias() {
    const STORAGE_KEY = 'mntIncidencias.tableState-v1';
    const controlsGrid = document.getElementById('controlsGrid');
    const theadRow = document.getElementById('theadRow');
    const tbody = document.getElementById('tbody');
    const table = document.getElementById('dataTable');
    const toggleControls = document.getElementById('toggleControls');
    const countSpan = document.getElementById('elementCount');

    if (!controlsGrid || !theadRow || !tbody || !table) {
      console.warn('[Incidencias] Elementos requeridos no encontrados, init cancelado.');
      return;
    }
    if (!tbody.dataset || !tbody.dataset.json) {
      console.warn('[Incidencias] tbody.dataset.json no presente, init cancelado.');
      return;
    }

    const defaultVisibleColumns = window.columnsIncidencias.map((col, index) => col.key === 'acciones' ? true : index < 5);

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
      desactivarBotones();
      loadState();
      buildControls();
      buildTable();
      initSearchUI();
      applyVisibilityFromState();
      updateControlsToggle();
      setupEventListeners();
      cargarBotonera();
    }
    
    function initSearchUI() {
	  const searchContainer = document.createElement('div');
	  searchContainer.id = 'searchContainer';
	  searchContainer.style.display = 'none';
	  searchContainer.innerHTML = `
	    <select id="columnSelector">
	      <option value="">-- Selecciona columna --</option>
	    </select>
	    <input type="text" id="searchInput" placeholder="Buscar..." disabled>
	  `;
	  document.querySelector('.page').insertBefore(searchContainer, document.querySelector('.table-wrap'));
	
	  const columnSelector = searchContainer.querySelector('#columnSelector');
	  const searchInput = searchContainer.querySelector('#searchInput');
	
	  // Poblar combo con cabeceras
	  window.columnsIncidencias.forEach((col, idx) => {
	      const opt = document.createElement('option');
	      opt.value = idx;
	      opt.textContent = col.label;
	      columnSelector.appendChild(opt);
	  });
	
	  columnSelector.addEventListener('change', () => {
	    searchInput.value = '';
	    searchInput.disabled = columnSelector.value === '';
	    if (!searchInput.disabled) searchInput.focus();
	    filterTable();
	  });
	
	  searchInput.addEventListener('input', filterTable);
	
	  function filterTable() {
	    const colIdx = parseInt(columnSelector.value, 10);
	    const term = searchInput.value.toLowerCase();
	
	    rowElements.forEach(tr => {
	      if (isNaN(colIdx)) {
	        tr.style.display = '';
	        return;
	      }
	      const cellText = (tr.children[colIdx].textContent || '').toLowerCase();
	      tr.style.display = cellText.includes(term) ? '' : 'none';
	    });
	
	    const visibleCount = rowElements.filter(tr => tr.style.display !== 'none').length;
	    countSpan.textContent = visibleCount;
	  }
	}
    
    // Exponer método para que la principal lo llame
	window.MntIncidencias.showSearch = function() {
	  const searchContainer = document.getElementById('searchContainer');
	  if (searchContainer) {
	    searchContainer.style.display = 'block';
	  }
	};


    function setupEventListeners() {
      controlsGrid.addEventListener('change', onControlsChange);
      if (toggleControls) {
        toggleControls.addEventListener('click', toggleControlsSection);
      }
    }

    function buildControls() {
      const frag = document.createDocumentFragment();
      window.columnsIncidencias.forEach((col, idx) => {
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
      window.columnsIncidencias.forEach((col, idx) => {
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

      const incidencias = JSON.parse(tbody.dataset.json || '[]');
      data = incidencias;

      const bodyFrag = document.createDocumentFragment();
      rowElements = data.map((row, i) => {
        const tr = document.createElement('tr');
        tr.setAttribute('data-idx', String(i));
        window.columnsIncidencias.forEach((col, colIdx) => {
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

      // Inicializar paginación genérica
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
        	return 'align-center';
        case 'text':
        	return 'align-left';
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
      tbody.textContent = '';
      rowElements.forEach(tr => tbody.appendChild(tr));
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
      window.columnsIncidencias.forEach((_, idx) => {
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

    function desactivarBotones(){
      document.getElementById('insertButton').disabled = true;
      document.getElementById('deleteButton').disabled = true;
      document.getElementById('printButton').disabled = true;
      document.getElementById('newsButton').disabled = true;
      document.getElementById('keyButton').disabled = true;
      document.getElementById('searchButton').disabled = true;
    }

    function cargarBotonera(){
      const printButton = document.getElementById('printButton');
      loadUrlPdf(printButton);
      document.getElementById('searchButton').disabled = false;
    }

    function loadUrlPdf(printButton) {
      const pdfUrl = document.getElementById('pdfUrl').value;
      if (pdfUrl) {
        printButton.disabled = false;
        printButton.setAttribute('onclick', `openPDF('${pdfUrl}')`);
      } else {
        printButton.disabled = true;
      }
    }
  };
})();
