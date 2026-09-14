function openController(type) {
    if(type === 'car') {
        document.getElementById('carController').classList.add('active');
        logConsole('SYS', 'Switched to RC Car profile');
    } else {
        alert(type.toUpperCase() + ' Controller module is in development!');
    }
}

function backToDashboard() {
    document.getElementById('carController').classList.remove('active');
}

// Simulating connection delay
setTimeout(() => {
    const status = document.getElementById('connectionStatus');
    status.className = 'connection-status connected';
    status.innerHTML = '<span class="icon">🟢</span> ESP32 Connected';
    logConsole('SYS', 'Bluetooth connection established');
}, 2000);

function logConsole(type, msg) {
    const log = document.getElementById('consoleLog');
    const entry = document.createElement('div');
    entry.className = `log-entry ${type.toLowerCase()}`;
    
    const time = new Date().toLocaleTimeString('en-US', { hour12: false });
    
    if (type === 'SENT') {
        entry.textContent = `[${time}] -> ${msg}`;
    } else {
        entry.textContent = `[${time}] [${type}] ${msg}`;
    }
    
    log.appendChild(entry);
    log.scrollTop = log.scrollHeight;
}

function triggerHaptic() {
    if(navigator.vibrate) navigator.vibrate(50);
    logConsole('SENT', 'EMERGENCY_STOP / HORN');
}

// Simple Joystick Logic
function setupJoystick(baseId) {
    const base = document.getElementById(baseId);
    const stick = base.querySelector('.joystick-stick');
    
    let isDragging = false;
    let centerX, centerY;
    const maxRadius = 30;

    base.addEventListener('mousedown', startDrag);
    base.addEventListener('touchstart', startDrag, {passive: false});

    function startDrag(e) {
        isDragging = true;
        const rect = base.getBoundingClientRect();
        centerX = rect.left + rect.width / 2;
        centerY = rect.top + rect.height / 2;
        move(e);
    }

    document.addEventListener('mousemove', move);
    document.addEventListener('touchmove', move, {passive: false});
    
    document.addEventListener('mouseup', endDrag);
    document.addEventListener('touchend', endDrag);

    let lastSent = 0;

    function move(e) {
        if (!isDragging) return;
        e.preventDefault();
        
        let clientX = e.clientX || e.touches[0].clientX;
        let clientY = e.clientY || e.touches[0].clientY;

        let deltaX = clientX - centerX;
        let deltaY = clientY - centerY;

        let distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        if (distance > maxRadius) {
            deltaX = (deltaX / distance) * maxRadius;
            deltaY = (deltaY / distance) * maxRadius;
        }

        stick.style.transform = `translate(${deltaX}px, ${deltaY}px)`;

        const now = Date.now();
        if(now - lastSent > 100) {
            let valX = Math.round((deltaX / maxRadius) * 255);
            let valY = Math.round((-deltaY / maxRadius) * 255);
            logConsole('SENT', `${baseId === 'joystickLeft' ? 'STEER' : 'THROTTLE'}:${valX},${valY}`);
            lastSent = now;
        }
    }

    function endDrag() {
        if(!isDragging) return;
        isDragging = false;
        stick.style.transform = `translate(0px, 0px)`;
        logConsole('SENT', `${baseId === 'joystickLeft' ? 'STEER' : 'THROTTLE'}:0,0`);
        if(navigator.vibrate) navigator.vibrate(10);
    }
}

setupJoystick('joystickLeft');
setupJoystick('joystickRight');
