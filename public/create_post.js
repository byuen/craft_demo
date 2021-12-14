 document.getElementById('submitPost').addEventListener('click', submitPost);

    function submitPost(e){
      e.preventDefault();

      let firstName = document.getElementById('firstName').value;
      let lastName = document.getElementById('lastName').value;
      let email = document.getElementById('email').value;
      let phone = document.getElementById('phone').value;
      let description = document.getElementById('description').value;
      let requirement = document.getElementById('requirement').value;

      fetch('http://localhost:8080/jobs', {
        method:'POST',
        headers: {
          'Accept': 'application/json',
          'Content-type':'application/json'
        },
        body:JSON.stringify({firstName:firstName, lastName:lastName, email:email,phone:phone, description:description,requirement:requirement})
      })
      .then((res) => res.json())
        .then((data=>{
           let output = "";

                output += `
            <ul class="list-group mb-3" id="${data.id}">
              <li class="list-group-item"><strong>Title:</strong> ${data.description}</li>
              <li class="list-group-item"><strong>Body:</strong> ${data.requirement}</li>
              <li class="list-group-item"><strong>createTimestamp:</strong> ${data.createTimestamp}</li>
              <li class="list-group-item"><strong>expireTimestamp:</strong> ${data.expireTimestamp}</li>
              <li class="list-group-item"><strong>isActive:</strong> ${data.active}</li>
              <li class="list-group-item"><strong>Time Left:</strong> ${data.timeLeft}</li>
              <li class="list-group-item"><strong>Poster:</strong> ${data.contact.firstName} ${data.contact.lastName}</li>
              <li class="list-group-item"><strong>Poster Email:</strong> ${data.contact.email} </li>
              <li class="list-group-item"><strong>Poster Phone:</strong> ${data.contact.phone} </li>
              <li class="list-group-item"><strong>Bids:</strong> ${data.bids!=null ? data.bids.length:''} </li>
               <li class="list-group-item"><strong>Lowest Bidder :</strong> ${data.lastBid!=null ?data.lastBid.bidder.firstName:''} ${data.lastBid!=null ? data.lastBid.bidder.lastName:''}</li>
                      <li class="list-group-item"><strong>Lowest Bid :</strong> ${data.lastBid!=null ? data.lastBid.amount:''}</li>

            </ul>

            <a href='http://localhost:8080/create_bid.html?id=${data.id}'>Bid</a>

          `;
            document.getElementById('posts').innerHTML = output;
        }))
    }