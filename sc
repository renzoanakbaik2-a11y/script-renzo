-- ==========================================================
-- TEST SCRIPT: AUTO PAUSE & RESUME (STAND SMART CHECKER)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TestAutoPauseUI") then
    PlayerGui.TestAutoPauseUI:Destroy()
end

-- 1. UI UTAMA
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TestAutoPauseUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 300, 0, 190)
MainFrame.Position = UDim2.new(0.5, -150, 0.4, -95)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Stroke = Instance.new("UIStroke")
Stroke.Color = Color3.fromRGB(255, 30, 60)
Stroke.Thickness = 2
Stroke.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "TEST: Auto Pause & Resume"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 12
Title.TextXAlignment = Enum.TextXAlignment.Left
Title.Parent = MainFrame

local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 20, 0, 20)
CloseBtn.Position = UDim2.new(1, -24, 0, 4)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
CloseBtn.Text = "X"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.SourceSansBold
CloseBtn.TextSize = 10
CloseBtn.Parent = MainFrame

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

local StatusBox = Instance.new("TextLabel")
StatusBox.Size = UDim2.new(1, -20, 0, 70)
StatusBox.Position = UDim2.new(0, 10, 0, 35)
StatusBox.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
StatusBox.TextColor3 = Color3.fromRGB(100, 255, 150)
StatusBox.Font = Enum.Font.Code
StatusBox.TextSize = 10
StatusBox.TextXAlignment = Enum.TextXAlignment.Left
StatusBox.TextYAlignment = Enum.TextYAlignment.Top
StatusBox.Text = "Status: Menunggu diaktifkan...\n- Stand Kosong = Otomatis Roll\n- Stand Terisi = Jeda (Pause buat dibeli)"
StatusBox.Parent = MainFrame

local BoxCorner = Instance.new("UICorner")
BoxCorner.CornerRadius = UDim.new(0, 6)
BoxCorner.Parent = StatusBox

-- SAKELAR UTAMA ON / OFF
local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 36)
ToggleBtn.Position = UDim2.new(0, 10, 0, 130)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "MASTER SAKELAR: OFF"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 2. FUNGSI PROXIMITY PROMPT
local function GetMyRollPrompt()
    local character = LocalPlayer.Character
    if not character or not character:FindFirstChild("HumanoidRootPart") then return nil end
    local myPos = character.HumanoidRootPart.Position

    local closestPrompt = nil
    local shortestDistance = 15

    local scriptable = Workspace:FindFirstChild("Scriptable")
    if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
        for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
            local rollBtn = building:FindFirstChild("RollButton")
            if rollBtn then
                for _, prompt in ipairs(rollBtn:GetDescendants()) do
                    if prompt:IsA("ProximityPrompt") then
                        local part = prompt.Parent
                        if part and part:IsA("BasePart") then
                            local dist = (part.Position - myPos).Magnitude
                            if dist < shortestDistance then
                                shortestDistance = dist
                                closestPrompt = prompt
                            end
                        end
                    end
                end
            end
        end
    end
    return closestPrompt
end

-- 3. FUNGSI CEK APAKAH STAND PULAU SEDANG TERISI NELAYAN
local function IsStandOccupied()
    local scriptable = Workspace:FindFirstChild("Scriptable")
    if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
        for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
            local rollStands = building:FindFirstChild("RollStands")
            if rollStands then
                for _, stand in ipairs(rollStands:GetChildren()) do
                    local reel = stand:FindFirstChild("Reel")
                    if reel and reel:FindFirstChild("Fisherman") then
                        -- Jika folder Fisherman ada isinya / karakternya muncul di stand
                        if #reel.Fisherman:GetChildren() > 0 then
                            return true
                        end
                    end
                end
            end
        end
    end
    return false
end

-- 4. LOGIKA UTAMA AUTO PAUSE & RESUME
local isMasterActive = false

ToggleBtn.MouseButton1Click:Connect(function()
    isMasterActive = not isMasterActive

    if isMasterActive then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "MASTER SAKELAR: ON (BERJALAN)"

        task.spawn(function()
            while isMasterActive do
                -- Cek apakah stand sedang terisi nelayan
                local occupied = IsStandOccupied()

                if occupied then
                    -- JIKA TERISI: Jeda roll (Pause) biar nelayannya bisa dibeli
                    StatusBox.Text = "Status: [PAUSED] Stand terisi nelayan!\nSilakan dibeli dulu, nanti lanjut roll otomatis."
                    task.wait(1) -- Cek berkala sampai stand dikosongkan/dibeli
                else
                    -- JIKA KOSONG: Lanjut gacha otomatis (Resume)
                    StatusBox.Text = "Status: [ROLLING] Stand kosong, lanjut gacha..."
                    
                    local prompt = GetMyRollPrompt()
                    if prompt then
                        if fireproximityprompt then
                            fireproximityprompt(prompt)
                        else
                            prompt:InputHoldBegin()
                            task.wait(0.05)
                            prompt:InputHoldEnd()
                        end
                    end

                    task.wait(0.7) -- Jeda aman antar roll
                end
            end
        end)
    else
        isMasterActive = false
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "MASTER SAKELAR: OFF"
        StatusBox.Text = "Status: Dimatikan."
    end
end)
