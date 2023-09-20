// START PAGINATION
function onClickPage(pageClick, currentPage, totalPages) {
    if (pageClick <= 0 || pageClick > totalPages || pageClick === currentPage) return;
    eventClickPage(pageClick);
}

var renderPagination = function(totalPages, currentPage, visiblePages) {

    let htmlPagination;
    let pageForm = initFormatPagination();
    htmlPagination = initBeforeRenderPage(pageForm, currentPage, totalPages)
    let listPage = getListNumberOfPage(visiblePages, totalPages, currentPage);
    htmlPagination = parsePagination(htmlPagination, listPage, currentPage, pageForm, totalPages);
    let pagination = document.getElementById('pagination');
    pagination.innerHTML = htmlPagination;
}

function parsePagination(htmlPagination, listPage, currentPage, pageForm, totalPages) {
    for (let index of listPage) {
        if (index === currentPage) {
            htmlPagination = htmlPagination + pageForm.pageActive.replace("__index_page__", index)
                .replace("__onClickPage__", index)
                .replace("__currentPage__", currentPage)
                .replace("__totalPages__", totalPages);
        } else {
            htmlPagination = htmlPagination + pageForm.elementPage.replace("__index_page__", index)
                .replace("__onClickPage__", index)
                .replace("__currentPage__", currentPage)
                .replace("__totalPages__", totalPages);
        }
    }
    htmlPagination = htmlPagination + pageForm.nextPage
        .replace("__onClickPage__", currentPage + 1)
        .replace("__currentPage__", currentPage)
        .replace("__totalPages__", totalPages);
    htmlPagination = htmlPagination + '</ul>';
    return htmlPagination;
}

function getListNumberOfPage(visiblePage, totalPage, currentPage) {
    let plusRight = true;
    let lastLeft = currentPage;
    let lastRight = currentPage;
    let results = [currentPage];
    if (visiblePage > totalPage) visiblePage = totalPage;
    visiblePage --;
    while(visiblePage > 0) {
        if (plusRight) {
            if (lastRight + 1 <= totalPage) {
                results.push(++lastRight)
            } else {
                plusRight = !plusRight;
                continue;
            }
        } else {
            if (lastLeft - 1 > 0) {
                lastLeft --;
                results.push(lastLeft);
            } else {
                plusRight = !plusRight;
                continue;
            }
        }
        plusRight = !plusRight;
        visiblePage--;
    }
    results.sort(function(a, b) {
        return a - b;
    });
    return results;
}

function initFormatPagination() {
    return {
        prevPage: `<li class="pagination-item "  onclick="onClickPage(__onClickPage__, __currentPage__, __totalPages__)">
                        <a  class="pagination-item__link">
                            <i class="pagination-item__icon fas fa-angle-left"></i>
                        </a>
                    </li>`,
        nextPage:
            `<li class="pagination-item" onclick="onClickPage(__onClickPage__, __currentPage__, __totalPages__)">
                        <a  class="pagination-item__link">
                            <i class="pagination-item__icon fas fa-angle-right"></i>
                        </a>
                    </li>`,

        elementPage:
            `<li class="pagination-item">
                <a  class="pagination-item__link" onclick="onClickPage(__onClickPage__, __currentPage__, __totalPages__)">
                    __index_page__
                </a>
            </li>`,

        pageActive:
            `<li class="pagination-item pagination-item__active" onclick="onClickPage(__onClickPage__, __currentPage__, __totalPages__)">
                <a  class="pagination-item__link">
                    __index_page__
                </a>
            </li>`,
    }
}

function initBeforeRenderPage(pageForm, currentPage, totalPages) {
    return '<ul class="pagination home-product__pagination">' + pageForm.prevPage
        .replace("__onClickPage__", currentPage - 1)
        .replace("__currentPage__", currentPage)
        .replace("__totalPages__", totalPages);
}

// END PAGINATION