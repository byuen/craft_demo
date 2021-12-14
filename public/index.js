 function getPosts(){
     fetch("http://localhost:8080/jobs")
     .then((res=>{
         return res.json()
     }))
     .then((data=>{
        let output = "";
         data.forEach(post => {
             output += `
         <ul class="list-group mb-3" id="${post.id}">
           <li class="list-group-item"><strong>Title:</strong> <a href='http://localhost:8080/create_bid.html?id=${post.id}' >${post.description}</a></li>
           <li class="list-group-item"><strong>Body:</strong> ${post.requirement}</li>
           <li class="list-group-item"><strong>createTimestamp:</strong> ${post.createTimestamp}</li>
           <li class="list-group-item"><strong>expireTimestamp:</strong> ${post.expireTimestamp}</li>
           <li class="list-group-item"><strong>isActive:</strong> ${post.active}</li>
           <li class="list-group-item"><strong>Time Left:</strong> ${post.timeLeft}</li>
           <li class="list-group-item"><strong>Poster:</strong> ${post.contact.firstName} ${post.contact.lastName}</li>
           <li class="list-group-item"><strong>Poster Email:</strong> ${post.contact.email} </li>
           <li class="list-group-item"><strong>Poster Phone:</strong> ${post.contact.phone} </li>
           <li class="list-group-item"><strong>Bids:</strong> ${post.bids.length} </li>

             <li class="list-group-item"><strong>Lowest Bidder :</strong> ${post.lastBid!=null ?post.lastBid.bidder.firstName:''} ${post.lastBid!=null ? post.lastBid.bidder.lastName:''}</li>
              <li class="list-group-item"><strong>Lowest Bid :</strong> ${post.lastBid!=null ? post.lastBid.amount:''}</li>

         </ul>
       `;
         })

         document.getElementById('posts').innerHTML = output;
     }))
 }


 function getActive(){
     fetch("http://localhost:8080/jobs?sortBy=bids&active=true")
     .then((res=>{
         return res.json()
     }))
     .then((data=>{
        let output = "";
         data.forEach(post => {
             output += `
         <ul class="list-group mb-3" id="${post.id}">
           <li class="list-group-item"><strong>Title:</strong><a href='http://localhost:8080/create_bid.html?id=${post.id}' >${post.description}</a></li>
           <li class="list-group-item"><strong>Body:</strong> ${post.requirement}</li>
           <li class="list-group-item"><strong>createTimestamp:</strong> ${post.createTimestamp}</li>
           <li class="list-group-item"><strong>expireTimestamp:</strong> ${post.expireTimestamp}</li>
           <li class="list-group-item"><strong>isActive:</strong> ${post.active}</li>
           <li class="list-group-item"><strong>Time Left:</strong> ${post.timeLeft}</li>
           <li class="list-group-item"><strong>Poster:</strong> ${post.contact.firstName} ${post.contact.lastName}</li>
           <li class="list-group-item"><strong>Poster Email:</strong> ${post.contact.email} </li>
           <li class="list-group-item"><strong>Poster Phone:</strong> ${post.contact.phone} </li>
           <li class="list-group-item"><strong>Bids:</strong> ${post.bids.length} </li>
             <li class="list-group-item"><strong>Lowest Bidder :</strong> ${post.lastBid.bidder.firstName} ${post.lastBid.bidder.lastName}</li>
              <li class="list-group-item"><strong>Lowest Bid :</strong> ${post.lastBid.amount}</li>
         </ul>
       `;
         })

         document.getElementById('active').innerHTML = output;
     }))
 }

getPosts();
getActive();