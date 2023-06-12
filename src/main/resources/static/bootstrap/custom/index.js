removeBtn = document.getElementById("removable")
const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
removeBtn?.addEventListener("click", e => {
    const target = e.target;
    if(target.type==="button") {
        const modalContent = document.getElementById("modalContent");
        const pattern = /(\d{1,2})/g;
        const bookIds = target.id.match(pattern);
        if(bookIds) {
            const bookId = bookIds[0];
            const confirmRemove = document.getElementById("confirmRemove");
            if(removeBtn.classList.contains("admin")) {
                confirmRemove.href = `/admin/book/remove/${bookId}`;
            } else {
                confirmRemove.href = `/cart/remove/${bookId}?quantity=1`;
            }
            const bookName = document.getElementById(`book-${bookId}`).innerText;
            console.log(`Book name: ${bookName}`)
            modalContent.innerText = `Remove ${bookName} ?`
        }

    }
})