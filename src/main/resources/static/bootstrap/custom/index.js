cartBtn = document.getElementById("cartTableBody")
cartBtn?.addEventListener("click", e => {
    const target = e.target;
    if(target.type==="button") {
        const modalContent = document.getElementById("modalContent");
        const pattern = /(\d{1,2})/g;
        const bookIds = target.id.match(pattern);
        if(bookIds) {
            const bookId = bookIds[0];
            const confirmRemove = document.getElementById("confirmRemove");
            confirmRemove.href = `/cart/remove/${bookId}?quantity=1`;
            const bookName = document.getElementById(`book-${bookId}`).innerText;
            modalContent.innerText = `Remove ${bookName} ?`
        }

    }
})