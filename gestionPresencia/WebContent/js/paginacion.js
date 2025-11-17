(function () {
window.Pagination = function Pagination(config) {
  let state = {
    currentPage: 1,
    pageSize: config.pageSize || 10,
    totalPages: 1
  };
  let rowElements = config.rows || [];
  let lastTotalItemsForPageSizeOptions = null;

  init();

  function init() {
    setupEventListeners();
    updatePagination();
  }

  function setupEventListeners() {
    if (config.firstPageBtn) config.firstPageBtn.addEventListener('click', () => goToPage(1));
    if (config.prevPageBtn) config.prevPageBtn.addEventListener('click', () => goToPage(state.currentPage - 1));
    if (config.nextPageBtn) config.nextPageBtn.addEventListener('click', () => goToPage(state.currentPage + 1));
    if (config.lastPageBtn) config.lastPageBtn.addEventListener('click', () => goToPage(state.totalPages));
    if (config.pageSizeSelector) {
      config.pageSizeSelector.addEventListener('change', (e) => {
        state.pageSize = parseInt(e.target.value);
        state.currentPage = 1;
        updatePagination();
      });
    }
  }

  function configurePaginationUi(totalItems) {
    if (!config.paginationContainer) return;

    if (totalItems <= 200) {
      config.paginationContainer.style.display = 'none';
      if (config.pageSizeSelector) {
        config.pageSizeSelector.innerHTML = '';
      }
      state.pageSize = Math.max(1, totalItems);
      state.currentPage = 1;
      return;
    }

    config.paginationContainer.style.display = '';

    if (!config.pageSizeSelector) return;

    if (lastTotalItemsForPageSizeOptions === totalItems && config.pageSizeSelector.options.length > 0) {
      const values = Array.from(config.pageSizeSelector.options).map(o => parseInt(o.value));
      if (!values.includes(state.pageSize)) {
        config.pageSizeSelector.value = String(values[0]);
        state.pageSize = values[0];
      }
      return;
    }

    lastTotalItemsForPageSizeOptions = totalItems;

    const percents = [0.10, 0.25, 0.50];
    const sizes = [...new Set(percents.map(p => Math.max(1, Math.floor(totalItems * p))))].sort((a, b) => a - b);

    config.pageSizeSelector.innerHTML = '';
    sizes.forEach(sz => {
      const opt = document.createElement('option');
      opt.value = String(sz);
      const pct = Math.round((sz / totalItems) * 100);
      opt.textContent = `${pct}% (${sz})`;
      config.pageSizeSelector.appendChild(opt);
    });

    const defaultValue = sizes.includes(state.pageSize) ? state.pageSize : sizes[0];
    state.pageSize = defaultValue;
    config.pageSizeSelector.value = String(defaultValue);
  }

  function updatePagination() {
    const totalItems = rowElements.length;
    configurePaginationUi(totalItems);

    const totalPages = Math.ceil(totalItems / state.pageSize) || 1;
    if (state.currentPage > totalPages) {
      state.currentPage = totalPages;
    } else if (state.currentPage < 1) {
      state.currentPage = 1;
    }
    state.totalPages = totalPages;

    const startIndex = (state.currentPage - 1) * state.pageSize;
    const endIndex = Math.min(startIndex + state.pageSize, totalItems);

    rowElements.forEach(tr => tr.style.display = 'none');
    rowElements.slice(startIndex, endIndex).forEach(tr => tr.style.display = '');

    if (config.pageInfo) {
      config.pageInfo.textContent = `Mostrando ${startIndex + 1}-${endIndex} de ${totalItems}`;
    }
    updatePaginationButtons();
  }

  function updatePaginationButtons() {
    if (config.firstPageBtn) config.firstPageBtn.disabled = state.currentPage === 1;
    if (config.prevPageBtn) config.prevPageBtn.disabled = state.currentPage === 1;
    if (config.nextPageBtn) config.nextPageBtn.disabled = state.currentPage === state.totalPages;
    if (config.lastPageBtn) config.lastPageBtn.disabled = state.currentPage === state.totalPages;
  }

  function goToPage(page) {
    if (page < 1 || page > state.totalPages) return;
    state.currentPage = page;
    updatePagination();
  }

  return {
    goToPage,
    updatePagination
  };
};
})();
