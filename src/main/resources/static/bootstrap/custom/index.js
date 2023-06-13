// const removeBtn = document.getElementById("removable")
// const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
// const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
// removeBtn?.addEventListener("click", e => {
//     const target = e.target;
//     if(target.type==="button") {
//         const modalContent = document.getElementById("modalContent");
//         const pattern = /(\d{1,2})/g;
//         const bookIds = target.id.match(pattern);
//         if(bookIds) {
//             const bookId = bookIds[0];
//             const confirmRemove = document.getElementById("confirmRemove");
//             if(removeBtn.classList.contains("admin")) {
//                 confirmRemove.href = `/admin/book/remove/${bookId}`;
//             } else {
//                 confirmRemove.href = `/cart/remove/${bookId}?quantity=1`;
//             }
//             const bookName = document.getElementById(`book-${bookId}`).innerText;
//             console.log(`Book name: ${bookName}`)
//             modalContent.innerText = `Remove ${bookName} ?`
//         }
//
//     }
// })
const e=document.getElementById("removable");[...document.querySelectorAll('[data-bs-toggle="tooltip"]')].map((e=>new bootstrap.Tooltip(e)));e?.addEventListener("click",(t=>{const o=t.target;if("button"===o.type){const t=document.getElementById("modalContent"),n=/(\d{1,2})/g,m=o.id.match(n);if(m){const o=m[0],n=document.getElementById("confirmRemove");e.classList.contains("admin")?n.href=`/admin/book/remove/${o}`:n.href=`/cart/remove/${o}?quantity=1`;const c=document.getElementById(`book-${o}`).innerText;console.log(`Book name: ${c}`),t.innerText=`Remove ${c} ?`}}}));