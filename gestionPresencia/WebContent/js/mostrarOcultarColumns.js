(function () {
window.TableColumnToggler = function (config) {
  const {
    tableId,
    controlsGridId,
    toggleControlsId,
    columnsConfig,
    storageKey
  } = config;

  const table = document.getElementById(tableId);
  const controlsGrid = document.getElementById(controlsGridId);
  const toggleControls = document.getElementById(toggleControlsId);

  if (!table || !controlsGrid) {
    console.warn('[TableColumnToggler] Elementos requeridos no encontrados.');
    return;
  }

  let state = {
    visibleColumns: columnsConfig.map((_, idx) => idx < 5), // primeras 5 visibles por defecto
    controlsCollapsed: true
  };

  init();

  function init() {
    loadState();
    buildControls();
    applyVisibilityFromState();
    setupEventListeners();
    updateControlsToggle();
  }

  function buildControls() {
    const frag = document.createDocumentFragment();
    columnsConfig.forEach((col, idx) => {
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
    });
    controlsGrid.textContent = '';
    controlsGrid.appendChild(frag);
  }

  function setupEventListeners() {
    controlsGrid.addEventListener('change', onControlsChange);
    if (toggleControls) {
      toggleControls.addEventListener('click', toggleControlsSection);
    }
  }

  function onControlsChange(e) {
    const target = e.target;
    if (target && target.matches('input[type="checkbox"][data-col-idx]')) {
      const idx = Number(target.getAttribute('data-col-idx'));
      toggleColumn(idx, target.checked);
    }
  }

  function toggleColumn(idx, visible) {
    state.visibleColumns[idx] = visible;
    applyVisibilityFromState();
    saveState();
  }

  function applyVisibilityFromState() {
    const cols = [...table.querySelectorAll(`thead th, tbody td`)];
    columnsConfig.forEach((_, idx) => {
      const show = state.visibleColumns[idx];
      const cells = cols.filter(el => el.classList.contains(`col-${idx}`));
      cells.forEach(cell => {
        cell.style.display = show ? '' : 'none';
      });
    });
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

  function loadState() {
    try {
      const raw = localStorage.getItem(storageKey);
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
      localStorage.setItem(storageKey, JSON.stringify(state));
    } catch (e) {
      console.warn('Error saving state:', e);
    }
  }
};
})();
